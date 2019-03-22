package com.udemy.sfg.recipeapp.repositories.reactive;

import com.udemy.sfg.recipeapp.domain.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String> {
}
