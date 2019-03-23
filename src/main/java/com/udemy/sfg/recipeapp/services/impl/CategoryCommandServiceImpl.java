package com.udemy.sfg.recipeapp.services.impl;

import com.udemy.sfg.recipeapp.commands.CategoryCommand;
import com.udemy.sfg.recipeapp.converters.CategoryToCategoryCommand;
import com.udemy.sfg.recipeapp.domain.Category;
import com.udemy.sfg.recipeapp.services.CategoryCommandService;
import com.udemy.sfg.recipeapp.services.CategoryService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryCommandServiceImpl implements CategoryCommandService {

    private final CategoryService categoryService;
    private final CategoryToCategoryCommand categoryToCategoryCommand;

    public CategoryCommandServiceImpl(CategoryService categoryService, CategoryToCategoryCommand categoryToCategoryCommand) {
        this.categoryService = categoryService;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
    }


    @Override
    public Flux<CategoryCommand> getAllCategoryCommands() {
        return categoryService.getAllCategories()
                .map(categoryToCategoryCommand::convert);
    }

    @Override
    public Mono<CategoryCommand> findCategoryCommandById(String id) {
        return categoryService.findById(id).map(categoryToCategoryCommand::convert);
    }
}
