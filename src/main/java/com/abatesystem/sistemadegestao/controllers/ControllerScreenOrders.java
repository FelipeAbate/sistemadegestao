package com.abatesystem.sistemadegestao.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerScreenOrders {

    @GetMapping("/pedidos")
    public String test(Model model) {
        return "orderscreen";
    }
}
