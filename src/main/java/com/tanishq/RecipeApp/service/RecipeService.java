package com.tanishq.RecipeApp.service;

import com.tanishq.RecipeApp.exceptions.RecipeNotFoundException;
import com.tanishq.RecipeApp.model.Recipe;
import com.tanishq.RecipeApp.model.RecipeSummary;
import com.tanishq.RecipeApp.repo.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    public List<RecipeSummary> searchRecipes(String query) {
        return recipeRepository.searchRecipes(query);
    }

    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id).orElseThrow(() -> new RecipeNotFoundException("Recipe with ID: "+id+" could not be found"));
    }
}
