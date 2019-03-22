package com.udemy.sfg.recipeapp.repositories.reactive;

import com.udemy.sfg.recipeapp.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class UnitOfMeasureReactiveRepositoryTest {

    @Autowired
    private UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;


    @BeforeEach
    void setUp() {
        unitOfMeasureReactiveRepository.deleteAll().block();
    }

    @Test
    void testSave() {
        UnitOfMeasure unitOfMeasure = UnitOfMeasure.builder().description("EACH").build();

        UnitOfMeasure saved = unitOfMeasureReactiveRepository.save(unitOfMeasure).block();

        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals(unitOfMeasure.getDescription(), saved.getDescription());
        assertEquals(1L, (long) unitOfMeasureReactiveRepository.count().block());
    }
}