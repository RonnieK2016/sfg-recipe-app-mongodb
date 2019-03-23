package com.udemy.sfg.recipeapp.services;

import com.udemy.sfg.recipeapp.domain.Recipe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface RecipeService {

    Flux<Recipe> getAllRecipes();

    Mono<Recipe> getById(String id);

    Mono<Recipe> saveRecipe(Recipe recipe);

    void deleteById(String id);
}
