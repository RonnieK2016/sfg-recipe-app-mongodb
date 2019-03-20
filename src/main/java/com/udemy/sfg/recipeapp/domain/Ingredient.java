package com.udemy.sfg.recipeapp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.math.BigDecimal;

@Data
public class Ingredient {
    @Id
    private String id;
    private String description;
    private BigDecimal amount;
    @DBRef
    UnitOfMeasure unitOfMeasure;
}
