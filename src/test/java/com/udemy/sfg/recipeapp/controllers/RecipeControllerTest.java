package com.udemy.sfg.recipeapp.controllers;

import com.udemy.sfg.recipeapp.commands.CategoryCommand;
import com.udemy.sfg.recipeapp.commands.RecipeCommand;
import com.udemy.sfg.recipeapp.domain.Recipe;
import com.udemy.sfg.recipeapp.exceptions.NotFoundException;
import com.udemy.sfg.recipeapp.services.CategoryCommandService;
import com.udemy.sfg.recipeapp.services.RecipeCommandService;
import com.udemy.sfg.recipeapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    @Mock
    private RecipeService recipeService;
    @Mock
    private RecipeCommandService recipeCommandService;
    @Mock
    private CategoryCommandService categoryCommandService;

    @InjectMocks
    private RecipeController recipeController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(recipeController)
                .setControllerAdvice(new ExceptionHandlerController())
                .build();
    }

    @Test
    void showRecipe() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId("1");
        when(recipeService.getById(anyString())).thenReturn(Mono.just(recipe));
        mockMvc.perform(get("/recipe/1/show/"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show_recipe"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService, times(1)).getById(anyString());
    }

    @Test
    void testGetNewRecipeForm() throws Exception {
        when(categoryCommandService.getAllCategoryCommands()).thenReturn(Flux.just(new CategoryCommand()));

        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void testPostNewRecipeForm() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId("2");

        when(recipeCommandService.saveRecipeCommand(any())).thenReturn(Mono.just(command));

        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string")
                .param("directions", "some directions")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/show"));
    }

    @Test
    void testPostNewRecipeFormValidationFail() throws Exception {
        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
        )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/recipeform"));
    }

    @Test
    void testGetUpdateView() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId("2");

        when(recipeCommandService.findRecipeCommandById(anyString())).thenReturn(Mono.just(command));

        when(categoryCommandService.getAllCategoryCommands()).thenReturn(Flux.just(new CategoryCommand()));

        mockMvc.perform(get("/recipe/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void testDeleteAction() throws Exception {
        mockMvc.perform(get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(recipeService, times(1)).deleteById(anyString());
    }

    @Test
    void testRecipeNotFound() throws Exception {
        when(recipeService.getById(anyString())).thenThrow(NotFoundException.class);
        mockMvc.perform(get("/recipe/1/show/"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404"));
        verify(recipeService).getById(anyString());
    }
}