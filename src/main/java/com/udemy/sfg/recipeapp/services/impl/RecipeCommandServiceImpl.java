package com.udemy.sfg.recipeapp.services.impl;

import com.udemy.sfg.recipeapp.commands.RecipeCommand;
import com.udemy.sfg.recipeapp.converters.RecipeCommandToRecipe;
import com.udemy.sfg.recipeapp.converters.RecipeToRecipeCommand;
import com.udemy.sfg.recipeapp.domain.Recipe;
import com.udemy.sfg.recipeapp.services.RecipeCommandService;
import com.udemy.sfg.recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RecipeCommandServiceImpl implements RecipeCommandService {

    private final RecipeService recipeService;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeCommandServiceImpl(RecipeService recipeService, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeService = recipeService;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);
        Recipe savedRecipe = recipeService.saveRecipe(detachedRecipe);

        log.debug("Saved recipe: {}", savedRecipe.getId());

        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    public RecipeCommand findRecipeCommandById(String id) {

        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipeService.getById(id));

        //enhance command object with id value
        if(recipeCommand.getIngredients() != null && recipeCommand.getIngredients().size() > 0){
            recipeCommand.getIngredients().forEach(rc -> {
                rc.setRecipeId(recipeCommand.getId());
            });
        }

        return recipeCommand;
    }
}
