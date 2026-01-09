package com.zds.bioengtsnapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Home Controller - Handles root path requests
 */
@Controller
public class HomeController {

    /**
     * Captures root path "/" requests and forwards to index.html
     */
    @GetMapping("")
    public String home() {
        return "forward:/index.html";
    }
}

