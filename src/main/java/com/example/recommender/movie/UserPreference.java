package com.example.recommender.movie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;

import java.util.List;

public record UserPreference(List<String> genres, double minRating, int releasedYear) {
    public String getUserPreferenceInTextFormat() throws JsonProcessingException {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Suggest me some good movies of the following genres: ");
        prompt.append(String.join(", ", genres).toUpperCase()).append(". ");
        prompt.append("Make sure they have at least IMDb rating above: ").append(minRating);
        prompt.append(" and have been released after year: ").append(releasedYear).append(". ");

        prompt.append("""
        I need the output to be in a JSONArray which can be easily deserialized into a Java list of following record: 
        record Movie(String title, double rating, String synopsis, int releasedYear) 
        """);

        prompt.append("Make sure your response only has the JSONArray for easy deserialization");
        return prompt.toString();
    }
}
