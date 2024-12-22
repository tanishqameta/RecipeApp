package com.tanishq.RecipeApp.config;

import com.tanishq.RecipeApp.model.RecipeWrapperDTO;
import com.tanishq.RecipeApp.repo.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class RecipeDataLoader implements ApplicationRunner {

    @Autowired
    private RecipeRepository recipeRepository;
    @Value("${third-party-api.recipes.url}")
    private String thirdPartyRecipeUrl;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        try {
            RecipeWrapperDTO recipesWrapper = restTemplate.getForObject(thirdPartyRecipeUrl, RecipeWrapperDTO.class);
            if (recipesWrapper != null && recipesWrapper.getRecipes() != null) {
                recipeRepository.saveAll(recipesWrapper.getRecipes());
            }
        } catch (RestClientException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
