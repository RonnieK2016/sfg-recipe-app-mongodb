package com.udemy.sfg.recipeapp.services;

import com.udemy.sfg.recipeapp.commands.CategoryCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface CategoryCommandService {

    Flux<CategoryCommand> getAllCategoryCommands();

    Mono<CategoryCommand> findCategoryCommandById(String id);
}
