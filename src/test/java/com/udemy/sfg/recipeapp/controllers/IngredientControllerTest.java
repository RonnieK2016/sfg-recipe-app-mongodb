package com.udemy.sfg.recipeapp.controllers;

import com.udemy.sfg.recipeapp.commands.IngredientCommand;
import com.udemy.sfg.recipeapp.commands.RecipeCommand;
import com.udemy.sfg.recipeapp.commands.UnitOfMeasureCommand;
import com.udemy.sfg.recipeapp.services.IngredientCommandService;
import com.udemy.sfg.recipeapp.services.RecipeCommandService;
import com.udemy.sfg.recipeapp.services.UnitOfMeasureCommandService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
class IngredientControllerTest {

    @Mock
    private RecipeCommandService recipeCommandService;
    @Mock
    private IngredientCommandService ingredientCommandService;
    @Mock
    private UnitOfMeasureCommandService unitOfMeasureCommandService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        IngredientController ingredientController = new IngredientController(recipeCommandService, ingredientCommandService, unitOfMeasureCommandService);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    void listIngredients() throws Exception {

        when(recipeCommandService.findRecipeCommandById(anyString())).thenReturn(Mono.just(new  RecipeCommand()));

        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeCommandService).findRecipeCommandById(anyString());
    }

    @Test
    void viewIngredientById() throws Exception {
        when(ingredientCommandService.findByRecipeIdAndIngredientId(anyString(), anyString()))
                .thenReturn((Mono.just(new IngredientCommand())));

        mockMvc.perform(get("/recipe/1/ingredient/2/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));
        verify(ingredientCommandService).findByRecipeIdAndIngredientId(anyString(), anyString());
    }

    @Test
    void testUpdateIngredientForm() throws Exception {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();

        //when
        when(ingredientCommandService.findByRecipeIdAndIngredientId(anyString(), anyString()))
                .thenReturn((Mono.just(ingredientCommand)));
        when(unitOfMeasureCommandService.getAllUoms()).thenReturn(Flux.just(new UnitOfMeasureCommand()));

        //then
        mockMvc.perform(get("/recipe/1/ingredient/2/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));
    }

    @Test
    void testSaveOrUpdate() throws Exception {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId("3");
        command.setRecipeId("2");

        //when
        when(ingredientCommandService.saveIngredientCommand(any())).thenReturn(Mono.just(command));

        //then
        mockMvc.perform(post("/recipe/2/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredient/3/show"));

    }

    @Test
    void testNewIngredientForm() throws Exception {

        when(unitOfMeasureCommandService.getAllUoms()).thenReturn(Flux.just(new UnitOfMeasureCommand()));

        //then
        mockMvc.perform(get("/recipe/1/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));

        verify(unitOfMeasureCommandService, times(1)).getAllUoms();

    }

    @Test
    void testDeleteRecipeIngredient() throws Exception {
        //then
        mockMvc.perform(get("/recipe/1/ingredient/2/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/ingredients"));
        verify(ingredientCommandService).deleteIngredientByRecipeIdAndIngredientId(anyString(), anyString());
    }
}