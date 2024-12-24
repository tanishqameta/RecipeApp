package com.tanishq.RecipeApp.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeSummary {
    @Id
    private Long id;
    private String name;
    private Integer prepTimeMinutes;
    private Integer cookTimeMinutes;
    private List<String> tags;
    private String image;
    private Double rating;
    private Integer reviewCount;
    private String cuisine;

    public RecipeSummary(@NotNull Recipe recipe) {
        this.id = recipe.getId();
        this.name = recipe.getName();
        this.prepTimeMinutes = recipe.getPrepTimeMinutes();
        this.cookTimeMinutes = recipe.getCookTimeMinutes();
        this.tags = recipe.getTags();
        this.image = recipe.getImage();
        this.rating = recipe.getRating();
        this.reviewCount = recipe.getReviewCount();
        this.cuisine = recipe.getCuisine();
    }
}
