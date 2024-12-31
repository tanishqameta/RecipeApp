package com.tanishq.RecipeApp.model;

import com.tanishq.RecipeApp.util.JsonConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;

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
    private String tags;
    private String image;
    private Double rating;
    private Integer reviewCount;
    private String cuisine;

    public RecipeSummary(@NotNull Recipe recipe) {
        this.id = recipe.getId();
        this.name = recipe.getName();
        this.prepTimeMinutes = recipe.getPrepTimeMinutes();
        this.cookTimeMinutes = recipe.getCookTimeMinutes();
        this.setTags(recipe.getTags());
        this.image = recipe.getImage();
        this.rating = recipe.getRating();
        this.reviewCount = recipe.getReviewCount();
        this.cuisine = recipe.getCuisine();
    }

    public List<String> getTags() {
        return JsonConverter.convertToList(this.tags);
    }

    public void setTags(List<String> tags) {
        this.tags = JsonConverter.convertToString(tags);
    }
}
