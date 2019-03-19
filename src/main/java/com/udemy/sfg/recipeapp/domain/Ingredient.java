package com.udemy.sfg.recipeapp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(exclude = {"recipe"})
public class Ingredient {
    private String id;
    private String description;
    private BigDecimal amount;
    UnitOfMeasure unitOfMeasure;
    private Recipe recipe;
}
