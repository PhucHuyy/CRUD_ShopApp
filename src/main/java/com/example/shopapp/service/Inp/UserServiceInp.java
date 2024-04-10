package com.example.shopapp.service.Inp;

import com.example.shopapp.dtos.UserRegisterDTO;
import com.example.shopapp.exceptions.DataNotFoundException;
import com.example.shopapp.models.User;
import org.springframework.stereotype.Service;

@Service
public interface UserServiceInp {
    User register(UserRegisterDTO userRegisterDTO) throws DataNotFoundException;

    String login(String phoneNumber, String password) throws DataNotFoundException;

}
