package com.tanishq.RecipeApp.controller;

import com.tanishq.RecipeApp.model.Recipe;
import com.tanishq.RecipeApp.model.RecipeSummary;
import com.tanishq.RecipeApp.service.RecipeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/recipes")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    private static final Logger logger = LoggerFactory.getLogger(RecipeController.class);
    @GetMapping("/search")
    public ResponseEntity<List<RecipeSummary>> searchRecipes(@RequestParam @Valid @NotBlank @Size(min=3) String query) {
        logger.info("Searching for recipes with query: {}", query);
        List<RecipeSummary> recipes = recipeService.searchRecipes(query);
        if (recipes.isEmpty()) {
            logger.warn("No recipes found for query: {}", query);
        } else {
            logger.info("Found {} recipes for query: {}", recipes.size(), query);
        }
        return ResponseEntity.ok(recipes);

    }

    @GetMapping("/{id}")
    @Validated
    public ResponseEntity<Recipe> getRecipe(@PathVariable Long id) {
        logger.info("Fetching recipe with ID: {}", id);
        Recipe recipe = recipeService.getRecipeById(id);
        if (recipe == null) {
            logger.error("Recipe not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
        logger.info("Found recipe with ID: {}", id);
        return ResponseEntity.ok(recipe);
    }
}
