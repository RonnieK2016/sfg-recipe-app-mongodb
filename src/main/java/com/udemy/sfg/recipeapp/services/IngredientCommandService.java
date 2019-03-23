package com.udemy.sfg.recipeapp.services;

import com.udemy.sfg.recipeapp.commands.IngredientCommand;
import reactor.core.publisher.Mono;

public interface IngredientCommandService {
    Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId);

    Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command);

    void deleteIngredientByRecipeIdAndIngredientId(String recipeId, String ingredientId);
}
