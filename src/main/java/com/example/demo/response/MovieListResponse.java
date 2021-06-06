package com.example.demo.response;

import com.example.demo.model.MovieEntity;
import lombok.Data;

import java.util.List;

@Data
public class MovieListResponse {

    MovieEntity topMovies;

    Integer averageRating;
}


