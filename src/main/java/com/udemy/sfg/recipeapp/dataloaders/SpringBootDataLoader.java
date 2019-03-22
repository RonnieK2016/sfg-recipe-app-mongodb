package com.udemy.sfg.recipeapp.dataloaders;

import com.udemy.sfg.recipeapp.domain.*;
import com.udemy.sfg.recipeapp.repositories.CategoryRepository;
import com.udemy.sfg.recipeapp.repositories.RecipeRepository;
import com.udemy.sfg.recipeapp.repositories.UnitOfMeasureRepository;
import com.udemy.sfg.recipeapp.repositories.reactive.CategoryReactiveRepository;
import com.udemy.sfg.recipeapp.repositories.reactive.RecipeReactiveRepository;
import com.udemy.sfg.recipeapp.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
@Profile("default")
public class SpringBootDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final CategoryRepository categoryRepository;

    public SpringBootDataLoader(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository, CategoryRepository categoryRepository) {
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(categoryRepository.count() == 0) {
            loadCategories();
        }
        if(unitOfMeasureRepository.count() == 0) {
            loadUnitsOfMeasure();
        }

        if(recipeRepository.count() == 0) {
            loadData();
        }
    }

    private void loadData() {

        log.debug("Staring loadData on bootstrap");

        Recipe recipe = new Recipe();
        recipe.setDescription("Perfect Guacamole");
        recipe.setCookTime(1);
        recipe.setDirections("1 Cut avocado, remove flesh\n" +
                "2 Mash with a fork\n" +
                "3 Add salt, lime juice, and the rest\n" +
                "4 Cover with plastic and chill to store");
        recipe.setDifficulty(Difficulty.EASY);
        Notes notes = new Notes();
        notes.setRecipeNotes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n" +
                "Feel free to experiment!");
        recipe.setNotes(notes);
        recipe.setPrepTime(10);
        recipe.setServings(4);
        Set<Category> categories = new HashSet<>();
        categories.add(categoryRepository.findByDescription("American").get());
        categories.add(categoryRepository.findByDescription("Mexican").get());
        recipe.setCategories(categories);

        Set<Ingredient> ingredients = new HashSet<>();

        Ingredient avocado = new Ingredient();
        avocado.setAmount(new BigDecimal(2));
        avocado.setDescription("Ripe Avocado");
        UnitOfMeasure none = unitOfMeasureRepository.findByDescription("Each").get();
        avocado.setUnitOfMeasure(none);
        ingredients.add(avocado);

        Ingredient salt = new Ingredient();
        salt.setDescription("Kosher Salt");
        salt.setAmount(new BigDecimal(0.5));
        salt.setUnitOfMeasure(unitOfMeasureRepository.findByDescription("Teaspoon").get());
        ingredients.add(salt);

        Ingredient limeJuice = new Ingredient();
        limeJuice.setDescription("Lime Juice");
        limeJuice.setAmount(new BigDecimal(1));
        limeJuice.setUnitOfMeasure(unitOfMeasureRepository.findByDescription("Tablespoon").get());
        ingredients.add(limeJuice);

        Ingredient redOnion = new Ingredient();
        redOnion.setDescription("Lime Juice");
        redOnion.setAmount(new BigDecimal(2));
        redOnion.setUnitOfMeasure(unitOfMeasureRepository.findByDescription("Tablespoon").get());
        ingredients.add(redOnion);

        Ingredient serranoChiles = new Ingredient();
        serranoChiles.setDescription("Serrano Chiles");
        serranoChiles.setAmount(new BigDecimal(2));
        serranoChiles.setUnitOfMeasure(none);
        ingredients.add(serranoChiles);

        Ingredient cilantro = new Ingredient();
        cilantro.setDescription("Cilantro");
        cilantro.setAmount(new BigDecimal(2));
        cilantro.setUnitOfMeasure(unitOfMeasureRepository.findByDescription("Tablespoon").get());
        ingredients.add(cilantro);

        Ingredient blackPepper = new Ingredient();
        blackPepper.setDescription("Black Pepper");
        blackPepper.setAmount(new BigDecimal(1));
        blackPepper.setUnitOfMeasure(unitOfMeasureRepository.findByDescription("Dash").get());
        ingredients.add(blackPepper);

        Ingredient ripeTomato = new Ingredient();
        ripeTomato.setDescription("Ripe Tomato");
        ripeTomato.setAmount(new BigDecimal(1));
        ripeTomato.setUnitOfMeasure(none);
        ingredients.add(ripeTomato);

        recipe.setIngredients(ingredients);

        recipeRepository.save(recipe);

        log.info("Recipe saved!");
    }

    private void loadCategories() {
        Category cat1 = new Category();
        cat1.setDescription("American");

        Category cat2 = new Category();
        cat2.setDescription("Italian");

        Category cat3 = new Category();
        cat3.setDescription("Mexican");

        Category cat4 = new Category();
        cat4.setDescription("Fast Food");

        categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4));
    }

    private void loadUnitsOfMeasure() {
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setDescription("Teaspoon");

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setDescription("Tablespoon");

        UnitOfMeasure uom3 = new UnitOfMeasure();
        uom3.setDescription("Cup");;

        UnitOfMeasure uom4 = new UnitOfMeasure();
        uom4.setDescription("Pinch");

        UnitOfMeasure uom5 = new UnitOfMeasure();
        uom5.setDescription("Ounce");

        UnitOfMeasure uom6 = new UnitOfMeasure();
        uom6.setDescription("Each");

        UnitOfMeasure uom7 = new UnitOfMeasure();
        uom7.setDescription("Pint");

        UnitOfMeasure uom8 = new UnitOfMeasure();
        uom8.setDescription("Dash");

        unitOfMeasureRepository.saveAll(Arrays.asList(uom1, uom2, uom3, uom4, uom5, uom6, uom7, uom8));
    }
}
