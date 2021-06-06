package com.example.demo.controller;

import com.example.demo.requests.MovieAddRequest;
import com.example.demo.response.AddResponse;
import com.example.demo.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2")
public class MovieController {

    private MovieService movieService;

    public MovieController(MovieService movieService){
        this.movieService=movieService;
    }

    @PostMapping(value = "/movie")
    public ResponseEntity<?> addMovie(@RequestBody MovieAddRequest movieAddRequest){
        boolean isMovieAdded = movieService.addMovie(movieAddRequest);
        AddResponse addResponse = new AddResponse(isMovieAdded);
        if (isMovieAdded)
            return new ResponseEntity<>(addResponse, HttpStatus.OK);
        else{
            addResponse.setErrorMessage("Failed to add the Movie");
            return new ResponseEntity<>(addResponse,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
