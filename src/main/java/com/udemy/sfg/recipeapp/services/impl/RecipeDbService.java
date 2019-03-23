package com.udemy.sfg.recipeapp.services.impl;

import com.udemy.sfg.recipeapp.commands.RecipeCommand;
import com.udemy.sfg.recipeapp.converters.RecipeCommandToRecipe;
import com.udemy.sfg.recipeapp.converters.RecipeToRecipeCommand;
import com.udemy.sfg.recipeapp.domain.Recipe;
import com.udemy.sfg.recipeapp.exceptions.NotFoundException;
import com.udemy.sfg.recipeapp.repositories.RecipeRepository;
import com.udemy.sfg.recipeapp.repositories.reactive.RecipeReactiveRepository;
import com.udemy.sfg.recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class RecipeDbService implements RecipeService {

    private final RecipeReactiveRepository recipeRepository;

    public RecipeDbService(RecipeReactiveRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Flux<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public Mono<Recipe> getById(String id) {
        return recipeRepository.findById(id);
    }

    @Override
    public Mono<Recipe> saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public void deleteById(String id) {
        recipeRepository.deleteById(id).block();
        log.debug("Recipe with id - {} - deleted", id);
    }
}
