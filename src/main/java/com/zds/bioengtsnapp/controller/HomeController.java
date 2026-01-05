package com.zds.bioengtsnapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 首页控制器 - 处理根路径请求
 */
@Controller
public class HomeController {

    /**
     * 捕获根路径 "/" 请求，转发到 index.html
     */
    @GetMapping("")
    public String home() {
        return "forward:/index.html";
    }
}

