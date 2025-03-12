package com.abatesystem.sistemadegestao.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerEstoque {

    @GetMapping("/estoque")
    public String estoque() {
        return "estoque_salvar_pdt";
    }
}
