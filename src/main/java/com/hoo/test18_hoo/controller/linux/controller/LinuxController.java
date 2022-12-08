package com.hoo.test18_hoo.controller.linux.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
@RequestMapping("/linux")
public class LinuxController {


    @GetMapping("/ubuntucommand")
    public String ubuntuCommand(Model model){
//        model.addAttribute("hello", "AAAAAAAAAAAAAAA");
        return "linux/ubuntu-command";
    }

}
