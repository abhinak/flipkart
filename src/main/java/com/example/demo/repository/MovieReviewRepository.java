package com.example.demo.repository;

import com.example.demo.model.MovieEntity;
import com.example.demo.model.MovieReviewEntity;
import com.example.demo.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieReviewRepository extends JpaRepository<MovieReviewEntity, Integer> {

    MovieReviewEntity findAllByUserEntityAndMovieEntity(UserEntity userEntity, MovieEntity movieEntity);

    List<MovieReviewEntity> findAllByUserEntity(UserEntity userEntity);

    List<MovieReviewEntity> findAllByMovieEntity(MovieEntity movieEntity);

    long countAllByUserEntity(UserEntity userEntity);

}
