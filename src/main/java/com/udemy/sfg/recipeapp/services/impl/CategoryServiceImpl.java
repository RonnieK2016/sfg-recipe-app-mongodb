package com.udemy.sfg.recipeapp.services.impl;

import com.udemy.sfg.recipeapp.domain.Category;
import com.udemy.sfg.recipeapp.repositories.reactive.CategoryReactiveRepository;
import com.udemy.sfg.recipeapp.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryReactiveRepository categoryRepository;

    public CategoryServiceImpl(CategoryReactiveRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public Flux<Category> getAllCategories() {
        return categoryRepository.findAll();
}

    @Override
    public Mono<Category> findById(String id) {
        return categoryRepository.findById(id);
    }
}
