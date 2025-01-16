package com.abatesystem.sistemadegestao.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerPutCalcas {

    @GetMapping("/put-calcas")
    public String test(Model model) {
        return "putcalcas";
    }
}
