package com.udemy.sfg.recipeapp.repositories.reactive;

import com.udemy.sfg.recipeapp.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class CategoryReactiveRepositoryIT {

    @Autowired
    private CategoryReactiveRepository reactiveRepository;

    @BeforeEach
    void setUp() {
        reactiveRepository.deleteAll().block();
    }

    @Test
    void findByDescription() {
        Category category = Category.builder().description("American").build();
        reactiveRepository.save(category).block();

        assertEquals(1L, (long) reactiveRepository.count().block());

        assertEquals("American", reactiveRepository.findByDescription("American").block().getDescription());
    }

    @Test
    void saveCategory() {
        Category category = Category.builder().description("American").build();
        Category savedCategory = reactiveRepository.save(category).block();

        assertNotNull(savedCategory);
        assertEquals(category.getDescription(), savedCategory.getDescription());
        assertNotNull(savedCategory.getId());
        assertEquals(1L, (long) reactiveRepository.count().block());
    }
}