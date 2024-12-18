package com.example.recommender.movie;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MovieRecommenderController {

    @Autowired
    private MovieRecommenderService movieRecommenderService;

    @PostMapping("/get-movies")
    public List<Movie> getMovies(@RequestBody UserPreference preference) throws JsonProcessingException {
        return movieRecommenderService.getRecommendedMovies(preference);
    }
}
