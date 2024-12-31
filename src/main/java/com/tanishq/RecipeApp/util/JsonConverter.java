package com.tanishq.RecipeApp.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class JsonConverter {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String convertToString(List<String> list) {
        try {
            return objectMapper.writeValueAsString(list);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing list to JSON string", e);
        }
    }

    public static List<String> convertToList(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing JSON string to list", e);
        }
    }
}
