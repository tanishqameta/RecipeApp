package com.tanishq.RecipeApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

import com.tanishq.RecipeApp.util.JsonConverter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Recipe {
    @Id
    private Long id;
    private String name;
    private String ingredients;
    private String instructions;
    private Integer prepTimeMinutes;
    private Integer cookTimeMinutes;
    private Integer servings;
    private String difficulty;
    private String cuisine;
    private Integer caloriesPerServing;
    private String tags;
    private String image;
    private Double rating;
    private Integer reviewCount;
    private String mealType;

    public Recipe(Long id, String name, List<String> ingredients, List<String> instructions, Integer prepTimeMinutes, Integer cookTimeMinutes, Integer servings, String difficulty, String cuisine, Integer caloriesPerServing, List<String> tags, String image, Double rating, Integer reviewCount, List<String> mealType) {
        this.id = id;
        this.name = name;
        this.setIngredients(ingredients);
        this.setInstructions(instructions);
        this.prepTimeMinutes = prepTimeMinutes;
        this.cookTimeMinutes = cookTimeMinutes;
        this.servings = servings;
        this.difficulty = difficulty;
        this.cuisine = cuisine;
        this.caloriesPerServing = caloriesPerServing;
        this.setTags(tags);
        this.image = image;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.setMealType(mealType);
    }

    public List<String> getIngredients() {
        return JsonConverter.convertToList(this.ingredients);
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = JsonConverter.convertToString(ingredients);
    }

    public List<String> getInstructions() {
        return JsonConverter.convertToList(this.instructions);
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = JsonConverter.convertToString(instructions);
    }

    public List<String> getTags() {
        return JsonConverter.convertToList(this.tags);
    }

    public void setTags(List<String> tags) {
        this.tags = JsonConverter.convertToString(tags);
    }

    public List<String> getMealType() {
        return JsonConverter.convertToList(this.mealType);
    }

    public void setMealType(List<String> mealType) {
        this.mealType = JsonConverter.convertToString(mealType);
    }
}