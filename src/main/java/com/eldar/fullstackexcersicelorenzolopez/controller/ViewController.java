package com.eldar.fullstackexerciselorenzolopez.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

    @RequestMapping(value = {"/", "/admin", "/product"})
    public String index() {
        return "index.html";
    }
}
