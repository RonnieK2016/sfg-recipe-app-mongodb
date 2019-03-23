package com.udemy.sfg.recipeapp.services;

import com.udemy.sfg.recipeapp.domain.Category;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryService {

    Flux<Category> getAllCategories();

    Mono<Category> findById(String id);
}
