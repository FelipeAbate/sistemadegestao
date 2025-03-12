package com.abatesystem.sistemadegestao.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerPedidos {

    @GetMapping("/pedidos")
    public String pedidos() {
        return "pedidos_views";
    }
}
