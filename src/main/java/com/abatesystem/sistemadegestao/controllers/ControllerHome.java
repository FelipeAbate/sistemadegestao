package com.abatesystem.sistemadegestao.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerHome {

    @GetMapping("/")
    public String showHome() {
        return "index";
    }
}
