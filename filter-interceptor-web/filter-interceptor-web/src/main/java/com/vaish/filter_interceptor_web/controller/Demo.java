package com.vaish.filter_interceptor_web.controller;

import com.vaish.filter_interceptor_web.service.DemoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Demo {
    private DemoService demoService;

    public Demo(DemoService demoService) {
        this.demoService = demoService;
    }

    @PostMapping
    public ResponseEntity<String> getUser(){
        demoService.getUser();
        return ResponseEntity.ok("Done");
    }
}
