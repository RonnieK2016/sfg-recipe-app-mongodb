package com.udemy.sfg.recipeapp.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Document
@EqualsAndHashCode(exclude = {"ingredients", "notes", "categories"})
public class Recipe {

    private String id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Byte[] image;
    private Set<Ingredient> ingredients = new HashSet<>();
    private Notes notes;
    private Difficulty difficulty;
    @DBRef
    private Set<Category> categories = new HashSet<>();
}
