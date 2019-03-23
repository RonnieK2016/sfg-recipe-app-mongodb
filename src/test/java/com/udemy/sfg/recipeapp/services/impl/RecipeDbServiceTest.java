package com.udemy.sfg.recipeapp.services.impl;

import com.udemy.sfg.recipeapp.commands.RecipeCommand;
import com.udemy.sfg.recipeapp.converters.RecipeCommandToRecipe;
import com.udemy.sfg.recipeapp.converters.RecipeToRecipeCommand;
import com.udemy.sfg.recipeapp.domain.Recipe;
import com.udemy.sfg.recipeapp.exceptions.NotFoundException;
import com.udemy.sfg.recipeapp.repositories.reactive.RecipeReactiveRepository;
import com.udemy.sfg.recipeapp.services.RecipeCommandService;
import com.udemy.sfg.recipeapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeDbServiceTest {

    private RecipeCommandService recipeCommandService;
    private RecipeService recipeService;

    @Mock
    private RecipeReactiveRepository recipeRepository;
    @Mock
    private RecipeCommandToRecipe recipeCommandToRecipe;
    @Mock
    private RecipeToRecipeCommand recipeToRecipeCommand;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeDbService(recipeRepository);
        recipeCommandService = new RecipeCommandServiceImpl(recipeService, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    void getRecipeByIdTest() {
        Recipe recipe = new Recipe();
        recipe.setId("1");

        when(recipeRepository.findById(anyString())).thenReturn(Mono.just(recipe));

        Recipe recipeReturned = recipeService.getById("1").block();

        assertNotNull(recipeReturned, "Null recipe returned");
        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    void getRecipeCommandByIdTest() {
        Recipe recipe = new Recipe();
        recipe.setId("1");

        when(recipeRepository.findById(anyString())).thenReturn(Mono.just(recipe));

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("1");

        when(recipeToRecipeCommand.convert(any())).thenReturn(recipeCommand);

        RecipeCommand commandById = recipeCommandService.findRecipeCommandById("1").block();

        assertNotNull(commandById, "Null recipe returned");
        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    void getRecipesTest() {

        Recipe recipe = new Recipe();

        //when(recipeService.getRecipes()).thenReturn(receipesData);
        when(recipeRepository.findAll()).thenReturn(Flux.just(recipe));

        List<Recipe> recipes = recipeService.getAllRecipes().collectList().block();

        assertNotNull(recipes);
        assertEquals(recipes.size(), 1);
        verify(recipeRepository, times(1)).findAll();
        verify(recipeRepository, never()).findById(anyString());
    }

    @Test
    void testDeleteById() {

        //given
        String idToDelete = "2";

        when(recipeRepository.deleteById(anyString())).thenReturn(Mono.empty());
        //when
        recipeService.deleteById(idToDelete);

        //no 'when', since method has void return type

        //then
        verify(recipeRepository, times(1)).deleteById(anyString());
    }
}