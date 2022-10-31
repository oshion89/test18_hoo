package com.hoo.test18_hoo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

    @GetMapping("/home")
    public ModelAndView goHome(HttpServletRequest request) {

        ModelAndView mav = new ModelAndView();
        List<String> resultList = new ArrayList<String>();

        resultList.add("AAA");
        resultList.add("BBB");
        resultList.add("CCC");
        resultList.add("DDD");
        resultList.add("EEE");
        resultList.add("FFF");

        mav.addObject("resultList",resultList);
        mav.setViewName("content/home");

        return mav;
    }

}

