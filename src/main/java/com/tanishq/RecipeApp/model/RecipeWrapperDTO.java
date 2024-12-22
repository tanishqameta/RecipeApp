package com.tanishq.RecipeApp.model;

import lombok.Data;

import java.util.List;

@Data
public class RecipeWrapperDTO {
    private List<Recipe> recipes;
    private int total;
    private int skip;
    private int limit;
}
