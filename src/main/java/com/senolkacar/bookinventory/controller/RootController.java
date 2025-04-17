package com.senolkacar.bookinventory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {
    @GetMapping("/")
    public String getRoot() {
        return "forward:/index.html";
    }
}
