package com.eldar.fullstackexcersicelorenzolopez.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {
    @RequestMapping("/")
    public String index() {
        return "index.html";
    }
}
