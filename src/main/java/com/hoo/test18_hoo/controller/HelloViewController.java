package com.hoo.test18_hoo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloViewController {

    @GetMapping("/helloview")
    public String hello(Model model){
        model.addAttribute("hello", "HelloWolrd");
        return "hello";
    }
}
