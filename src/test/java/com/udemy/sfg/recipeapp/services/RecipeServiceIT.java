package com.udemy.sfg.recipeapp.services;

import com.udemy.sfg.recipeapp.commands.RecipeCommand;
import com.udemy.sfg.recipeapp.converters.RecipeCommandToRecipe;
import com.udemy.sfg.recipeapp.converters.RecipeToRecipeCommand;
import com.udemy.sfg.recipeapp.domain.Recipe;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class RecipeServiceIT {

    private static final String NEW_DESCRIPTION = "New Description";

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeCommandService recipeCommandService;

    @Autowired
    private RecipeCommandToRecipe recipeCommandToRecipe;

    @Autowired
    private RecipeToRecipeCommand recipeToRecipeCommand;

    @Test
    void testSaveOfDescription() {
        //given
        Flux<Recipe> recipesFuture = recipeService.getAllRecipes();

        List<Recipe> recipes = recipesFuture.collectList().block();

        assertNotNull(recipes);

        assertFalse(recipes.isEmpty());

        Recipe testRecipe = recipes.get(0);

        assertNotNull(testRecipe);

        RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);

        //when
        testRecipeCommand.setDescription(NEW_DESCRIPTION);
        RecipeCommand savedRecipeCommand = recipeCommandService.saveRecipeCommand(testRecipeCommand).block();

        //then
        assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
        assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
        assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
        assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
    }
}
