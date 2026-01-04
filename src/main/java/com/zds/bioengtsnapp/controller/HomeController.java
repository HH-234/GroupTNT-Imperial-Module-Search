package com.zds.bioengtsnapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 兜底首页：确保访问 "/" 一定能看到 static/index.html
 * 避免在外部 Tomcat 上欢迎页机制未生效时出现 Tomcat 原生 404。
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "forward:/index.html";
    }
}



