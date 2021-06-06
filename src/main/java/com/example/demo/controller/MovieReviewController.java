package com.example.demo.controller;

import com.example.demo.model.MovieEntity;
import com.example.demo.model.MovieReviewEntity;
import com.example.demo.requests.MovieAddRequest;
import com.example.demo.requests.MovieReviewAddRequest;
import com.example.demo.response.AddResponse;
import com.example.demo.service.MovieReviewService;
import io.swagger.models.auth.In;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v2")
public class MovieReviewController {

    private MovieReviewService movieReviewService;


    public MovieReviewController(MovieReviewService movieReviewService){
        this.movieReviewService = movieReviewService;
    }

    @PostMapping(value = "/movieReview")
    public ResponseEntity<?> addReview(@RequestBody MovieReviewAddRequest movieReviewAddRequest){
        boolean isReviewAdded = movieReviewService.addMovieReview(movieReviewAddRequest);
        AddResponse addResponse = new AddResponse(isReviewAdded);
        if (isReviewAdded)
            return new ResponseEntity<>(addResponse, HttpStatus.OK);
        else{
            addResponse.setErrorMessage("Failed to add the Movie Review");
            return new ResponseEntity<>(addResponse,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping(value = "/movieReview")
    public ResponseEntity<?> modifyReview(@RequestBody MovieReviewAddRequest movieReviewAddRequest){
        boolean isReviewUpdated = movieReviewService.updateMovieReview(movieReviewAddRequest);
        AddResponse addResponse = new AddResponse(isReviewUpdated);
        if (isReviewUpdated)
            return new ResponseEntity<>(addResponse, HttpStatus.OK);
        else{
            addResponse.setErrorMessage("Failed to update the Movie review");
            return new ResponseEntity<>(addResponse,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping(value = "/movieReview/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Integer id){
        boolean isReviewDeleted = movieReviewService.deleteMovieReview(id);
        AddResponse addResponse = new AddResponse(isReviewDeleted);
        if (isReviewDeleted)
            return new ResponseEntity<>(addResponse, HttpStatus.OK);
        else{
            addResponse.setErrorMessage("Failed to delete the Movie review");
            return new ResponseEntity<>(addResponse,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/movieReview/{id}")
    public ResponseEntity<?> getReview(@PathVariable Integer id){
        List<MovieReviewEntity> movieReviewEntities = movieReviewService.listReviewsForAUser(id);
        return new ResponseEntity<>(movieReviewEntities,HttpStatus.OK);
    }

    @GetMapping(value = "/topMovies/{genre}")
    public ResponseEntity<?> topReview(@PathVariable String genre){
        List<MovieEntity> topMovies = movieReviewService.findAllMovieRevieEntityByGenre(genre);
        return new ResponseEntity<>(topMovies,HttpStatus.OK);
    }
}
