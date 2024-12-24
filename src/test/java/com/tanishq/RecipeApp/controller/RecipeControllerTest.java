package com.tanishq.RecipeApp.controller;

import com.tanishq.RecipeApp.model.Recipe;
import com.tanishq.RecipeApp.model.RecipeSummary;
import com.tanishq.RecipeApp.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.times;

@WebMvcTest(RecipeController.class)
class RecipeControllerTest {
    @Mock
    private RecipeService recipeService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testSearchRecipes_FoundResults() throws Exception {
        String query = "pasta";
        RecipeSummary recipeSummary1 = new RecipeSummary(new Recipe(1L, "Pasta Carbonara", null, null, 10, 20, 4, "Easy", "Italian", 300, Arrays.asList("Quick", "Dinner"), "image1.jpg", 4.5, 100, Arrays.asList("Lunch", "Dinner")));
        RecipeSummary recipeSummary2 = new RecipeSummary(new Recipe(2L, "Pasta Alfredo", null, null, 15, 25, 2, "Medium", "Italian", 400, Arrays.asList("Comfort Food"), "image2.jpg", 4.7, 150, Arrays.asList("Dinner")));

        List<RecipeSummary> mockRecipes = Arrays.asList(recipeSummary1, recipeSummary2);

        when(recipeService.searchRecipes(query)).thenReturn(mockRecipes);

        mockMvc.perform(get("/recipes/search")
                        .param("query", query))
                .andExpect(status().isOk());

        verify(recipeService, times(1)).searchRecipes(query);
    }

    @Test
    void testSearchRecipes_NoResults() throws Exception {
        String query = "nonexistentrecipe";

        when(recipeService.searchRecipes(query)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/recipes/search")
                        .param("query", query))
                .andExpect(status().isOk());

        verify(recipeService, times(1)).searchRecipes(query);
    }

    @Test
    void testGetRecipe_Found() throws Exception {
        Long recipeId = 1L;
        Recipe recipe = new Recipe(recipeId, "Spaghetti Bolognese", Arrays.asList("Pasta", "Tomato Sauce", "Beef"), Arrays.asList("Boil pasta", "Cook sauce"), 10, 20, 4, "Easy", "Italian", 500, Arrays.asList("Dinner", "Quick"), "image.jpg", 4.8, 200, Arrays.asList("Lunch"));

        when(recipeService.getRecipeById(recipeId)).thenReturn(recipe);

        mockMvc.perform(get("/recipes/{id}", recipeId))
                .andExpect(status().isOk());

        verify(recipeService, times(1)).getRecipeById(recipeId);
    }

    @Test
    void testGetRecipe_NotFound() throws Exception {
        Long recipeId = 1L;

        when(recipeService.getRecipeById(recipeId)).thenReturn(null);

        mockMvc.perform(get("/recipes/{id}", recipeId))
                .andExpect(status().isNotFound());

        verify(recipeService, times(1)).getRecipeById(recipeId);
    }
}