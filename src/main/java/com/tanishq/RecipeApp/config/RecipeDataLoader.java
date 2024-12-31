package com.tanishq.RecipeApp.config;

import com.tanishq.RecipeApp.model.RecipeWrapperDTO;
import com.tanishq.RecipeApp.repo.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

@Component
public class RecipeDataLoader implements ApplicationRunner {

    @Autowired
    private RecipeRepository recipeRepository;
    @Value("${third-party-api.recipes.url}")
    private String thirdPartyRecipeUrl;

    @Retryable(
            value = { RestClientException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    @Override
    public void run(ApplicationArguments args) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        try {
            RecipeWrapperDTO recipesWrapper = restTemplate.getForObject(thirdPartyRecipeUrl, RecipeWrapperDTO.class);
            if (recipesWrapper != null && recipesWrapper.getRecipes() != null) {
                Mono<Void> saveRecipesMono = recipeRepository.saveAll(
                        recipesWrapper.getRecipes().stream().map(recipe -> {
                            recipe.setId(null);
                            return recipe;
                        }).toList()
                        )
                        .then();  // Convert to Mono<Void> after saving all recipes

                saveRecipesMono.subscribe();  // Trigger the save operation
            }
        } catch (RestClientException e) {
            throw new RuntimeException("Could not load data from 3rd Party api" + e.getMessage());
        }
    }
}
