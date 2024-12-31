package com.tanishq.RecipeApp;

import com.tanishq.RecipeApp.controller.RecipeController;
import com.tanishq.RecipeApp.model.Recipe;
import com.tanishq.RecipeApp.model.RecipeSummary;
import com.tanishq.RecipeApp.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class RecipeAppApplicationTests {

	@Mock
	private RecipeService recipeService;

	@InjectMocks
	private RecipeController recipeController;

	private WebTestClient webTestClient;

	@BeforeEach
	void setUp() {
		webTestClient = WebTestClient.bindToController(recipeController).build();
	}

	@Test
	void testSearchRecipes_FoundResults() {
		String query = "pasta";
		RecipeSummary recipeSummary1 = new RecipeSummary(new Recipe(1L, "Pasta Carbonara", null, null, 10, 20, 4, "Easy", "Italian", 300, Arrays.asList("Quick", "Dinner"), "image1.jpg", 4.5, 100, Arrays.asList("Lunch", "Dinner")));
		RecipeSummary recipeSummary2 = new RecipeSummary(new Recipe(2L, "Pasta Alfredo", null, null, 15, 25, 2, "Medium", "Italian", 400, Arrays.asList("Comfort Food"), "image2.jpg", 4.7, 150, Arrays.asList("Dinner")));

		Flux<RecipeSummary> mockRecipes = Flux.just(recipeSummary1, recipeSummary2);

		when(recipeService.searchRecipes(query)).thenReturn(mockRecipes);

		webTestClient.get()
				.uri(uriBuilder -> uriBuilder.path("/recipes/search")
						.queryParam("query", query)
						.build())
				.exchange()
				.expectStatus().isOk();

		verify(recipeService, times(1)).searchRecipes(query);
	}

	@Test
	void testSearchRecipes_NoResults() {
		String query = "nonexistentrecipe";

		when(recipeService.searchRecipes(query)).thenReturn(Flux.empty());

		webTestClient.get()
				.uri(uriBuilder -> uriBuilder.path("/recipes/search")
						.queryParam("query", query)
						.build())
				.exchange()
				.expectStatus().isOk();

		verify(recipeService, times(1)).searchRecipes(query);
	}

	@Test
	void testGetRecipe_Found() {
		Long recipeId = 1L;
		Recipe recipe = new Recipe(recipeId, "Spaghetti Bolognese", Arrays.asList("Pasta", "Tomato Sauce", "Beef"), Arrays.asList("Boil pasta", "Cook sauce"), 10, 20, 4, "Easy", "Italian", 500, Arrays.asList("Dinner", "Quick"), "image.jpg", 4.8, 200, Arrays.asList("Lunch"));

		when(recipeService.getRecipeById(recipeId)).thenReturn(Mono.just(recipe));

		webTestClient.get()
				.uri("/recipes/{id}", recipeId)
				.exchange()
				.expectStatus().isOk()
				.expectBody();

		verify(recipeService, times(1)).getRecipeById(recipeId);
	}

	@Test
	void testGetRecipe_NotFound() {
		Long recipeId = 1L;

		when(recipeService.getRecipeById(recipeId)).thenReturn(Mono.empty());

		webTestClient.get()
				.uri("/recipes/{id}", recipeId)
				.exchange()
				.expectStatus().isNotFound();

		verify(recipeService, times(1)).getRecipeById(recipeId);
	}
}

