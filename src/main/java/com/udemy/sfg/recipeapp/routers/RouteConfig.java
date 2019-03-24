package com.udemy.sfg.recipeapp.routers;

import com.udemy.sfg.recipeapp.domain.Recipe;
import com.udemy.sfg.recipeapp.services.RecipeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class RouteConfig {

    @Bean
    RouterFunction<?> recipes(RecipeService recipeService) {
        return RouterFunctions.route(GET("/api/recipes"),
                serviceRequest -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(recipeService.getAllRecipes(), Recipe.class)
        );
    }
}
