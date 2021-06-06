package com.example.demo.requests;

import lombok.Data;

@Data
public class MovieReviewAddRequest {

    private Integer  movieId;
    private Integer userId;
    private Integer review;

}
