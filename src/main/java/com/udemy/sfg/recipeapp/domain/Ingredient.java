package com.udemy.sfg.recipeapp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class Ingredient {
    private String id = UUID.randomUUID().toString();
    private String description;
    private BigDecimal amount;
    @DBRef
    UnitOfMeasure unitOfMeasure;
}
