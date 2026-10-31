package com.vaish.filter_interceptor_web.service;

import org.springframework.stereotype.Service;

@Service
public class DemoService {

    public String getUser(){
        System.out.println("Created");
        return "Created User";
    }
}
