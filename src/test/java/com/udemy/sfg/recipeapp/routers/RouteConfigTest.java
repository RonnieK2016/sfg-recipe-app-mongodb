package com.udemy.sfg.recipeapp.routers;

import com.udemy.sfg.recipeapp.domain.Recipe;
import com.udemy.sfg.recipeapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.*;
import static reactor.core.publisher.Mono.when;

class RouteConfigTest {

    private WebTestClient webTestClient;

    @Mock
    private RecipeService recipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        RouteConfig routeConfig = new RouteConfig();
        RouterFunction<?> routerFunction = routeConfig.recipes(recipeService);
        webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();
    }

    @Test
    void testGetRecipes() {

        when(recipeService.getAllRecipes()).thenReturn(Flux.just());

        webTestClient.get().uri("/api/recipes")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testGetRecipesData() {

        when(recipeService.getAllRecipes()).thenReturn(Flux.just(new Recipe()));

        webTestClient.get().uri("/api/recipes")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Recipe.class);
    }

}