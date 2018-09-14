package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController2 {
 
    @RequestMapping(value = "/{locale:en|fr|vi}/login2")
    public String login2(Model model) {
        return "login2";
    }
 
}