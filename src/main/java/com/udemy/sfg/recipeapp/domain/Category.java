package com.udemy.sfg.recipeapp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Document
@EqualsAndHashCode(exclude = "recipes")
public class Category {
    @Id
    private String id;
    private String description;
    @DBRef
    private Set<Recipe> recipes = new HashSet<>();
}
