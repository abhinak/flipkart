package com.example.demo.repository;

import com.example.demo.model.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<MovieEntity, Integer> {

    List<MovieEntity> findAllByGenre(String genre);

    List<MovieEntity> findAllByReleaseYear(Integer id);
}
