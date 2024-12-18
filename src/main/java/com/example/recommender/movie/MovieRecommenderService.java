package com.example.recommender.movie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.models.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class MovieRecommenderService {

    @Value("${openai.api.key}")
    private String apiKey;


    public List<Movie> getRecommendedMovies(UserPreference userPreference) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        List<Movie> movies = Collections.emptyList();

        OpenAIClient client = OpenAIOkHttpClient.builder()
                .apiKey(apiKey)
                .build();

        String userPrompt = userPreference.getUserPreferenceInTextFormat();

        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .messages(List.of(ChatCompletionMessageParam.ofChatCompletionUserMessageParam(ChatCompletionUserMessageParam.builder()
                        .role(ChatCompletionUserMessageParam.Role.USER)
                        .content(ChatCompletionUserMessageParam.Content.ofTextContent(userPrompt))
                        .build())))
                .model(ChatModel.GPT_4)
                .build();
        ChatCompletion chatCompletion = client.chat().completions().create(params);

        Optional<? extends List<ChatCompletion.Choice>> choicesOptional = chatCompletion._choices().asKnown();
        if(choicesOptional.isPresent()) {
            List<ChatCompletion.Choice> choices = choicesOptional.get();
            ChatCompletion.Choice choice = choices.get(0);
            Optional<String> content = choice.message().content();

            if(content.isPresent()) {
                movies = mapper.readValue(content.get(), new TypeReference<List<Movie>>() {});
            }
        }
        return movies;
    }
}
