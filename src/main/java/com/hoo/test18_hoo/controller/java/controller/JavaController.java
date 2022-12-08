package com.hoo.test18_hoo.controller.java.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/java")
public class JavaController {



    @GetMapping("/endecryption")
    public String encodingSha(Model model){
//        model.addAttribute("hello", "AAAAAAAAAAAAAAA");
        return "java/en-decryption";
    }


    @GetMapping("/loadingspeed")
    public String measureLoadingSpeed(Model model){
        return "java/measure-loading-speed";
    }


    @GetMapping("/showwebhtml")
    public String showWebHtml(Model model){
        return "java/show-web-html";
    }

    @GetMapping("/gettoken")
    public String getJWTToken(Model model){
        return "java/jwt-token";
    }


}
