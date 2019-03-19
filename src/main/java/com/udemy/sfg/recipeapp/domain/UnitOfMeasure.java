package com.udemy.sfg.recipeapp.domain;

import lombok.*;

import javax.persistence.Entity;

@Entity
@Data
public class UnitOfMeasure {
    private String id;

    private String description;

}
