package com.udemy.sfg.recipeapp.services.impl;

import com.udemy.sfg.recipeapp.commands.UnitOfMeasureCommand;
import com.udemy.sfg.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.udemy.sfg.recipeapp.repositories.UnitOfMeasureRepository;
import com.udemy.sfg.recipeapp.repositories.reactive.UnitOfMeasureReactiveRepository;
import com.udemy.sfg.recipeapp.services.UnitOfMeasureCommandService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureCommandServiceImpl implements UnitOfMeasureCommandService {

    private final UnitOfMeasureReactiveRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;


    public UnitOfMeasureCommandServiceImpl(UnitOfMeasureReactiveRepository unitOfMeasureRepository,
                                           UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.uomConverter = uomConverter;
    }

    @Override
    public Flux<UnitOfMeasureCommand> getAllUoms() {

        return unitOfMeasureRepository.findAll().map(uomConverter::convert);
    }
}
