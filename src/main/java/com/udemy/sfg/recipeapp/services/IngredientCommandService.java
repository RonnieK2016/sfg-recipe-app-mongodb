package com.udemy.sfg.recipeapp.services;

import com.udemy.sfg.recipeapp.commands.IngredientCommand;

public interface IngredientCommandService {
    IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand command);

    void deleteIngredientByRecipeIdAndIngredientId(String recipeId, String ingredientId);
}
