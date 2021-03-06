package com.udemy.sfg.recipeapp.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnitOfMeasure {
    @Id
    private String id;
    private String description;

}
