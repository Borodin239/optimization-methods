package ru.metopt.itmo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class ConvertController {

    @PostMapping("/home")
    public String home() {
        return "Hello";
    }
}