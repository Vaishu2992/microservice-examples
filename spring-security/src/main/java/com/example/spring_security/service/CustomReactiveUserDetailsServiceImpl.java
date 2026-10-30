package com.example.spring_security.service;

import com.example.spring_security.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@AllArgsConstructor
public class CustomReactiveUserDetailsServiceImpl implements ReactiveUserDetailsService {
    private final UserRepository userRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return Mono.fromCallable(() ->
                        userRepository.findByUserName(username)
                                .orElseThrow(() ->
                                        new UsernameNotFoundException(
                                                "User not found: " + username)))
                .subscribeOn(Schedulers.boundedElastic())
                .map(user -> User
                        .withUsername(user.getUserName())
                        .password(user.getPassword())
                        .authorities("ROLE_USER")
                        .build());
    }
}
