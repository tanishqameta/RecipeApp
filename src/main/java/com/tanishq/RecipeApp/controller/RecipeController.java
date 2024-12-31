package com.tanishq.RecipeApp.controller;

import com.tanishq.RecipeApp.model.Recipe;
import com.tanishq.RecipeApp.model.RecipeSummary;
import com.tanishq.RecipeApp.repo.RecipeRepository;
import com.tanishq.RecipeApp.service.RecipeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/recipes")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeRepository recipeRepo;

    private static final Logger logger = LoggerFactory.getLogger(RecipeController.class);

    @GetMapping("/search")
    public Flux<RecipeSummary> searchRecipes(@RequestParam @Valid @NotBlank @Size(min=3) String query) {
        logger.info("Searching for recipes with query: {}", query);
        return recipeService.searchRecipes(query)
                .onBackpressureBuffer(100,
                        droppedItem -> logger.warn("Dropped recipe due to backpressure: {}", droppedItem))
                .limitRate(10)
                .doOnComplete(() -> logger.info("Finished searching recipes for query: {}", query))
                .doOnError(error -> logger.error("Error occurred while searching for recipes: {}", error.getMessage()));
    }

    @GetMapping("/{id}")
    @Validated
    public Mono<Recipe> getRecipe(@PathVariable Long id) {
        logger.info("Fetching recipe with ID: {}", id);
        return recipeService.getRecipeById(id)
                .doOnNext(recipe -> logger.info("Found recipe with ID: {}", id))
                .doOnError(error -> logger.error(error.getMessage()));
    }
}
