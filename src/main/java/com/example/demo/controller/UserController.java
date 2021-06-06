package com.example.demo.controller;

import com.example.demo.requests.UserAddRequest;
import com.example.demo.response.AddResponse;
import com.example.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping(value = "/user")
    public ResponseEntity<?> addUser(@RequestBody UserAddRequest userAddRequest){
        boolean isUserAdded = userService.addUser(userAddRequest);
        AddResponse addResponse = new AddResponse(isUserAdded);
        if (isUserAdded)
            return new ResponseEntity<>(addResponse, HttpStatus.OK);
        else{
            addResponse.setErrorMessage("Failed to add the User");
            return new ResponseEntity<>(addResponse,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
