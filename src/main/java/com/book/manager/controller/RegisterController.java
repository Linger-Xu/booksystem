package com.book.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterController {

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // 返回视图名，假设有一个 register.html 文件
    }
}
