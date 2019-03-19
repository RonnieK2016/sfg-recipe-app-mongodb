package com.udemy.sfg.recipeapp.controllers;

import com.udemy.sfg.recipeapp.commands.CategoryCommand;
import com.udemy.sfg.recipeapp.commands.RecipeCommand;
import com.udemy.sfg.recipeapp.domain.Recipe;
import com.udemy.sfg.recipeapp.exceptions.NotFoundException;
import com.udemy.sfg.recipeapp.services.CategoryCommandService;
import com.udemy.sfg.recipeapp.services.RecipeCommandService;
import com.udemy.sfg.recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Set;

@Controller
@Slf4j
public class RecipeController {

    private static final String RECIPE_FORM_VIEW = "recipe/recipeform";

    private RecipeService recipeService;
    private RecipeCommandService recipeCommandService;
    private CategoryCommandService categoryCommandService;

    public RecipeController(RecipeService recipeService, RecipeCommandService recipeCommandService
            , CategoryCommandService categoryCommandService) {
        this.recipeService = recipeService;
        this.recipeCommandService = recipeCommandService;
        this.categoryCommandService = categoryCommandService;
    }

    @GetMapping
    @RequestMapping("/recipe/{id}/show")
    public String showRecipe(@PathVariable String id,
            Model model) {
        Recipe recipe = recipeService.getById(id);
        model.addAttribute("recipe", recipe);
        return "recipe/show_recipe";
    }

    @GetMapping
    @RequestMapping("recipe/new")
    public String showRecipeForm(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        model.addAttribute( "categories", categoryCommandService.getAllCategoryCommands());
        return RECIPE_FORM_VIEW;
    }

    @GetMapping
    @RequestMapping("recipe/{id}/update")
    public String updateRecipeForm(@PathVariable String id, Model model) {

        RecipeCommand recipeCommand = recipeCommandService.findRecipeCommandById(id);
        Set<CategoryCommand> categoryCommands = categoryCommandService.getAllCategoryCommands();

        if(!CollectionUtils.isEmpty(recipeCommand.getCategories())) {
            categoryCommands.removeIf(categoryCommand -> recipeCommand.getCategories().contains(categoryCommand));
        }

        model.addAttribute("recipe", recipeCommand);
        model.addAttribute("categories", categoryCommands);


        return RECIPE_FORM_VIEW;
    }

    @GetMapping
    @RequestMapping("recipe/{id}/delete")
    public String deleteRecipe(@PathVariable String id) {
        recipeService.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("recipe")
    public String addOrUpdateRecipe(@Valid @ModelAttribute("recipe") RecipeCommand recipeCommand,
                                    BindingResult bindingResult) {

        if(bindingResult.hasErrors()){

            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));

            return RECIPE_FORM_VIEW;
        }

        RecipeCommand savedCommand = recipeCommandService.saveRecipeCommand(recipeCommand);
        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNotFoundException(Exception exception) {

        log.error("Handling NotFoundException", exception);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }
}
