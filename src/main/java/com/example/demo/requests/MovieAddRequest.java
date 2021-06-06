package com.example.demo.requests;

import lombok.Data;

@Data
public class MovieAddRequest {

    private String name;
    private Integer yearOfRelease;
    private String genre;
}
