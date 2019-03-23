package com.udemy.sfg.recipeapp.services.impl;

import com.udemy.sfg.recipeapp.commands.IngredientCommand;
import com.udemy.sfg.recipeapp.converters.IngredientCommandToIngredient;
import com.udemy.sfg.recipeapp.converters.IngredientToIngredientCommand;
import com.udemy.sfg.recipeapp.domain.Ingredient;
import com.udemy.sfg.recipeapp.domain.Recipe;
import com.udemy.sfg.recipeapp.repositories.UnitOfMeasureRepository;
import com.udemy.sfg.recipeapp.services.IngredientCommandService;
import com.udemy.sfg.recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.swing.text.html.Option;

@Service
@Slf4j
public class IngredientCommandServiceImpl implements IngredientCommandService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final RecipeService recipeService;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientCommandServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand
            , RecipeService recipeService, IngredientCommandToIngredient ingredientCommandToIngredient, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.recipeService = recipeService;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
        Recipe recipe = recipeService.getById(recipeId).block();

        if(recipe == null) {
            log.warn("Recipe is not found by id {} ", recipeId);
            return null;
        }

        Ingredient ingredient = recipe.getIngredients().stream()
                .filter( in -> ingredientId.equals(in.getId()))
                .findFirst()
                .orElse(null);
        if(ingredient == null) {
            log.warn("Ingredient is not found by id {} ", ingredientId);
            return null;
        }

        IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(ingredient);
        ingredientCommand.setRecipeId(recipeId);
        return Mono.just(ingredientCommand);
    }

    @Override
    public Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command) {
        Recipe recipe = recipeService.getById(command.getRecipeId()).block();

        if(recipe == null){
            log.error("Recipe not found for id: {}", command.getRecipeId());
            return Mono.just(new IngredientCommand());
        }
        else {
            Ingredient ingredient;
            // new ingredient case
            if(command.getId() == null) {
                ingredient = ingredientCommandToIngredient.convert(command);
                recipe.getIngredients().add(ingredient);
            }
            else
            {
                ingredient = recipe.getIngredients()
                        .stream()
                        .filter(ingredientEl -> command.getId().equals(ingredientEl.getId()))
                        .findFirst()
                        .orElse(null);
                if(ingredient != null){
                    ingredient.setDescription(command.getDescription());
                    ingredient.setAmount(command.getAmount());
                    ingredient.setUnitOfMeasure(unitOfMeasureRepository
                            .findById(command.getUnitOfMeasure().getId())
                            .orElseThrow(() -> new RuntimeException("UOM NOT FOUND")));
                }
                else {
                    ingredient = ingredientCommandToIngredient.convert(command);
                    recipe.getIngredients().add(ingredient);
                }
            }

            command.setId(ingredient.getId());
        }

        Recipe savedRecipe = recipeService.saveRecipe(recipe).block();

        command.setRecipeId(savedRecipe.getId());

        return Mono.just(command);
    }

    @Override
    public void deleteIngredientByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
        Recipe recipe = recipeService.getById(recipeId).block();

        if(recipe == null){
            throw new RuntimeException("Recipe not found for id: " + recipeId);
        }

        Ingredient ingredient =
                recipe.getIngredients().stream().filter(ingredient1 -> ingredientId.equals(ingredient1.getId()))
                        .findFirst().orElse(null);

        if(ingredient != null) {
            recipe.getIngredients().remove(ingredient);
            recipeService.saveRecipe(recipe).block();
        }
    }
}
