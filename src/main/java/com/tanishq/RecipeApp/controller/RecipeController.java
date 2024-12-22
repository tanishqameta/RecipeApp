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

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @GetMapping("/search")
    public ResponseEntity<List<RecipeSummary>> searchRecipes(@RequestParam @Valid @NotBlank @Size(min=3) String query) {
        List<RecipeSummary> recipes = recipeService.searchRecipes(query);
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/{id}")
    @Validated
    public ResponseEntity<Recipe> getRecipe(@PathVariable Long id) {
        Recipe recipe = recipeService.getRecipeById(id);
        return ResponseEntity.ok(recipe);
    }
}
