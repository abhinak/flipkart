package com.example.demo.service;

import com.example.demo.model.MovieEntity;
import com.example.demo.repository.MovieRepository;
import com.example.demo.requests.MovieAddRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MovieService {

    private MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    public boolean addMovie(MovieAddRequest movieAddRequest){

        try{
            MovieEntity movieEntity = new MovieEntity();
            movieEntity.setGenre(movieAddRequest.getGenre());
            movieEntity.setName(movieAddRequest.getName());
            movieEntity.setReleaseYear( movieAddRequest.getYearOfRelease());
            movieRepository.save(movieEntity);
        }
        catch (Exception e){
            log.error("Failed to add the movie with name : " + movieAddRequest.getName());
            return false;
        }
        return true;
    }
}
