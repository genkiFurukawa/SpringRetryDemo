package com.example.demo.controller;

import com.example.demo.usecase.SampleUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    private final SampleUseCase sampleUsecase;

    @Autowired
    public SampleController (
            SampleUseCase sampleUsecase
    ) {
        this.sampleUsecase = sampleUsecase;
    }

    @GetMapping("/hello")
    public String hello() {
        sampleUsecase.hello();
        return "hello!!";
    }
}
