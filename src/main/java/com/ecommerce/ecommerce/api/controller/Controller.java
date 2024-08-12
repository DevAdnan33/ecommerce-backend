package com.ecommerce.ecommerce.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.api.model.LoginBody;
import com.ecommerce.ecommerce.api.model.LoginResponse;
import com.ecommerce.ecommerce.api.model.RegistrationBody;
import com.ecommerce.ecommerce.exceptions.UserAlreadyExistsException;
import com.ecommerce.ecommerce.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class Controller {

    @Autowired
    private UserService userService;

    @SuppressWarnings("rawtypes")
    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody RegistrationBody registrationBody) {
        try {
            userService.registerUser(registrationBody);
            return ResponseEntity.ok().build();
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginBody loginBody) {
        String jwt = userService.loginUser(loginBody);
        if (jwt == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            LoginResponse response = new LoginResponse();
            response.setJwt(jwt);
            return ResponseEntity.ok(response);
        }
    }

}
