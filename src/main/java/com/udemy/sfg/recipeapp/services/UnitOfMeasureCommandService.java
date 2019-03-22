package com.udemy.sfg.recipeapp.services;

import com.udemy.sfg.recipeapp.commands.UnitOfMeasureCommand;
import reactor.core.publisher.Flux;

import java.util.Set;

public interface UnitOfMeasureCommandService {
    Flux<UnitOfMeasureCommand> getAllUoms();
}
