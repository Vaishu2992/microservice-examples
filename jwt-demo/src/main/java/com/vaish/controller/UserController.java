package com.vaish.controller;

import com.vaish.entity.UserEntity;
import com.vaish.service.UserServiceImpl;
import com.vaish.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final ReactiveAuthenticationManager authenticationManager;
    private final UserServiceImpl userService;
    private final JwtUtil jwtUtil;

    @GetMapping("/dumm")
    public UserEntity dummyEntity(){
        return new UserEntity();
    }

    @PostMapping("/createuser")
    public UserEntity createUser(@RequestBody UserEntity userEntity){
        return userService.createUser(userEntity);
    }

    @PostMapping("/authenticateUser")
    public Mono<Boolean> authenticateUser(@RequestBody UserEntity request) {
        return authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(),request.getPassword()))
                .map(Authentication::isAuthenticated)
                .onErrorReturn(false);
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<Map<String, String>>> login(@RequestBody UserEntity request) {
        return authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()))
                .flatMap(authentication -> {
                    // 1. Authentication successful, get the UserDetails principal
                    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

                    // 2. Generate the JWT token
                    String token = jwtUtil.generateToken(userDetails);

                    // 3. Return the token in the response body (HTTP 200 OK)
                    return Mono.just(ResponseEntity.ok(Map.of("token", token)));
                })
                // 4. Handle authentication failure (Bad credentials, user not found, etc.)
                .onErrorResume(error -> {
                    // Fix: Wrap the error message in a Map to match the signature
                    return Mono.just(ResponseEntity
                            .status(HttpStatus.UNAUTHORIZED)
                            .body(Map.of("error", "Invalid username or password")));
                });
    }

}
