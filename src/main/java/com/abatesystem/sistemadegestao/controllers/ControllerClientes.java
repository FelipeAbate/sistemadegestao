package com.abatesystem.sistemadegestao.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerClientes {

    @GetMapping("/clientes")
    public String clientes() {
        return "clientes_views";
    }
}
