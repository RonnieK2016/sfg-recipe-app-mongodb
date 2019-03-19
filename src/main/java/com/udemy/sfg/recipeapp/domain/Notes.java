package com.udemy.sfg.recipeapp.domain;

import lombok.*;

@Data
@EqualsAndHashCode(exclude = {"recipe"})
public class Notes {
    private String id;
    private Recipe recipe;
    private String recipeNotes;
}