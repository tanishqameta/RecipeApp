package com.tanishq.RecipeApp.repo;

import com.tanishq.RecipeApp.model.Recipe;
import com.tanishq.RecipeApp.model.RecipeSummary;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface RecipeRepository extends R2dbcRepository<Recipe, Long> {
    Flux<RecipeSummary> findByNameContainingIgnoreCaseOrCuisineContainingIgnoreCase(String name, String cuisine);
}