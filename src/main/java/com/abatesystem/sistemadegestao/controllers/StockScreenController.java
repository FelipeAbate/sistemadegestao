package com.abatesystem.sistemadegestao.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StockScreenController {

    @GetMapping("/estoque")
    public String showForm() {
        return "formtosaveproductinstock";
    }
}
