package com.udemy.sfg.recipeapp.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
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
    private Set<Category> categories = new HashSet<>();


}
