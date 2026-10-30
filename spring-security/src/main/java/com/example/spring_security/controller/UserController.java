package com.example.spring_security.controller;

import com.example.spring_security.entity.UserEntity;
import com.example.spring_security.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final ReactiveAuthenticationManager authenticationManager;
    private final UserServiceImpl userService;

    @GetMapping("/dumm")
    public UserEntity dummyEntity(){
        return new UserEntity();
    }

    @PostMapping("/createuser")
    public UserEntity createUser(@RequestBody UserEntity userEntity){
        return userService.createUser(userEntity);
    }

    @PostMapping("/authenticateUser")
    public Mono<Boolean> login(@RequestBody UserEntity request) {
        return authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(),request.getPassword()))
                .map(Authentication::isAuthenticated)
                .onErrorReturn(false);
    }
}
