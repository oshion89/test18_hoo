package com.hoo.test18_hoo.controller.java.controller;


import com.hoo.test18_hoo.controller.java.service.JavaRestService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/java")
public class JavaRestController {

    private final JavaRestService javaRestService;

    @GetMapping("/sha256")
    public String encodingSha(
            @RequestParam(required = true) String type
            , @RequestParam(required = true) String text){
        return javaRestService.encodingSha(type, text);
    }



    @GetMapping("/measureloadingspeed")
    public String measureLoadingSpeed(@RequestParam(required = true) String url){
        return javaRestService.measureLoadingSpeed(url);
    }


    @GetMapping("/apishowwebhtml")
    public String showWebHtml(@RequestParam(required = true) String url){
        return javaRestService.showWebHtml(url);
    }

    @GetMapping("/apigettoken")
    public String getJWTToken(@RequestParam(required = true) String text){
        return javaRestService.getJWTToken(text);
    }

    @GetMapping("/apidecodetoken")
    public String decodeToken(@RequestParam(required = true) String text){
        return javaRestService.decodeToken(text);
    }

}
