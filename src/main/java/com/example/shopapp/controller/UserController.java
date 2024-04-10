package com.example.shopapp.controller;

import com.example.shopapp.dtos.UserLoginDTO;
import com.example.shopapp.dtos.UserRegisterDTO;
import com.example.shopapp.exceptions.DataNotFoundException;
import com.example.shopapp.service.Inp.UserServiceInp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    UserServiceInp userServiceInp;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) throws DataNotFoundException {
        if(!userRegisterDTO.getPassword().equals(userRegisterDTO.getRetypePassword())){
            return new ResponseEntity<>("Password does not match", HttpStatus.BAD_REQUEST);
        }

        userServiceInp.register(userRegisterDTO);

        return new ResponseEntity<>("Register Successfully " + userRegisterDTO, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userLoginDTO) throws DataNotFoundException {
        // trả về token
        String token = userServiceInp.login(userLoginDTO.getPhoneNumber(), userLoginDTO.getPassword());
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

}
