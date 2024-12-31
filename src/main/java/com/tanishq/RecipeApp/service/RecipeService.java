package com.tanishq.RecipeApp.service;

import com.tanishq.RecipeApp.exceptions.RecipeNotFoundException;
import com.tanishq.RecipeApp.model.Recipe;
import com.tanishq.RecipeApp.model.RecipeSummary;
import com.tanishq.RecipeApp.repo.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    public Flux<RecipeSummary> searchRecipes(String query) {
        return recipeRepository.findByNameContainingIgnoreCaseOrCuisineContainingIgnoreCase(query, query);
    }

    public Mono<Recipe> getRecipeById(Long id) {
        return recipeRepository.findById(id).
                switchIfEmpty(Mono.error(new RecipeNotFoundException("Recipe with ID: "+id+" could not be found")));
    }
}
