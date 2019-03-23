package com.udemy.sfg.recipeapp.services.impl;

import com.udemy.sfg.recipeapp.commands.RecipeCommand;
import com.udemy.sfg.recipeapp.converters.RecipeCommandToRecipe;
import com.udemy.sfg.recipeapp.converters.RecipeToRecipeCommand;
import com.udemy.sfg.recipeapp.domain.Recipe;
import com.udemy.sfg.recipeapp.services.RecipeCommandService;
import com.udemy.sfg.recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {
        return recipeService.saveRecipe(recipeCommandToRecipe.convert(command))
                .map(recipeToRecipeCommand::convert);
    }

    @Override
    public Mono<RecipeCommand> findRecipeCommandById(String id) {


        return recipeService.getById(id).map( recipe -> {
            RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);
            return  recipeCommand;
        });
    }
}
