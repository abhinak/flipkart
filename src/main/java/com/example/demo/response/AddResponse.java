package com.example.demo.response;

import lombok.Data;

@Data
public class AddResponse {

    public AddResponse(boolean isSuccess){
        this.isSuccess=isSuccess;
    }

    public AddResponse(boolean isSuccess, String errorMessage){
        this.isSuccess=isSuccess;
        this.errorMessage=errorMessage;
    }

    private boolean isSuccess;
    private String errorMessage;
}
