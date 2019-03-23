package com.udemy.sfg.recipeapp.services;

import com.udemy.sfg.recipeapp.commands.RecipeCommand;
import reactor.core.publisher.Mono;

public interface RecipeCommandService {

    Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command);

    Mono<RecipeCommand> findRecipeCommandById(String id);
}
