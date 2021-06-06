package com.example.demo.service;

import com.example.demo.model.UserEntity;
import com.example.demo.model.UserType;
import com.example.demo.repository.UserRepository;
import com.example.demo.requests.UserAddRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public boolean addUser(UserAddRequest userAddRequest){
        try{
            UserEntity userEntity = new UserEntity();
            userEntity.setEmaidID(userAddRequest.getEmailId());
            userEntity.setName(userAddRequest.getName());
            userEntity.setType(UserType.VIEWER.name());
            userRepository.save(userEntity);
        }
        catch (Exception e){
            log.error("Failed to add the User with name : " + userAddRequest.getName());
            return  false;
        }
        return true;
    }
}
