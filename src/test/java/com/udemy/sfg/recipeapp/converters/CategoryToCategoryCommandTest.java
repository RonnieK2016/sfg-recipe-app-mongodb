package com.udemy.sfg.recipeapp.converters;

import com.udemy.sfg.recipeapp.commands.CategoryCommand;
import com.udemy.sfg.recipeapp.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryToCategoryCommandTest {

    private static final String ID_VALUE = "1";
    private static final String DESCRIPTION = "descript";
    private CategoryToCategoryCommand convter;

    @BeforeEach
    void setUp() {
        convter = new CategoryToCategoryCommand();
    }

    @Test
    void testNullObject() {
        assertNull(convter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(convter.convert(new Category()));
    }

    @Test
    void convert() {
        //given
        Category category = new Category();
        category.setId(ID_VALUE);
        category.setDescription(DESCRIPTION);

        //when
        CategoryCommand categoryCommand = convter.convert(category);

        //then
        assertEquals(ID_VALUE, categoryCommand.getId());
        assertEquals(DESCRIPTION, categoryCommand.getDescription());

    }
}