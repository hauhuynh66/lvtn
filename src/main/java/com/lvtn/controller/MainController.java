package com.lvtn.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MainController {
    @GetMapping("/manage")
    public ModelAndView main(){
        return new ModelAndView("manager");
    }
}
