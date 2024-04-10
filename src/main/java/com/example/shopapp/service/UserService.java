package com.example.shopapp.service;

import com.example.shopapp.components.JwtTokenUtil;
import com.example.shopapp.dtos.UserRegisterDTO;
import com.example.shopapp.exceptions.DataNotFoundException;
import com.example.shopapp.models.Role;
import com.example.shopapp.models.User;
import com.example.shopapp.repository.RoleRepository;
import com.example.shopapp.repository.UserRepository;
import com.example.shopapp.service.Inp.UserServiceInp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserServiceInp {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    AuthenticationManager authenticationManager;
    @Override
    public User register(UserRegisterDTO userRegisterDTO) throws DataNotFoundException {
        // kiểm tra sdt xem đã có đki chưa
        String phoneNumber = userRegisterDTO.getPhoneNumber();
        if(userRepository.existsByPhoneNumber(phoneNumber)){
            throw new DataIntegrityViolationException("Phone number already exists");
        }

        User user = new User();
        user.setFullName(userRegisterDTO.getFullName());
        user.setPhoneNumber(userRegisterDTO.getPhoneNumber());
        user.setPassword(userRegisterDTO.getPassword());

        Role existingRole = roleRepository.findById(userRegisterDTO.getRoleID())
                .orElseThrow(() -> new DataNotFoundException("Cannot find role with id = "+ userRegisterDTO.getRoleID()));
        user.setRole(existingRole);

        if(userRegisterDTO.getFacebookAccountID() == 0 && userRegisterDTO.getGoogleAccountID() == 0){
            String password = userRegisterDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            user.setPassword(encodedPassword);
        }

        userRepository.save(user);

        return user;
    }

    @Override
    public String login(String phoneNumber, String password) throws DataNotFoundException {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
        if(optionalUser.isEmpty()){
            throw new DataNotFoundException("Invalid phonenumber / password");
        }

        User existingUser = optionalUser.get();
        // check password
        if(existingUser.getFacebookAccountID() == 0 && existingUser.getGoogleAccountID() == 0){
            if(!passwordEncoder.matches(password, existingUser.getPassword())){
                throw new BadCredentialsException("Wrong phone number or password");
            }
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                phoneNumber, password
        );

        //authenticate with Spring Security
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }
}
