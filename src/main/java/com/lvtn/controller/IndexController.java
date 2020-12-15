package com.lvtn.controller;

import com.lvtn.service.DataService;
import com.lvtn.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
    @Autowired
    private DataService dataService;
    @Autowired
    private RestService restService;

    @GetMapping({"/","/login"})
    public ModelAndView login(){
        return new ModelAndView("login");
    }

    @GetMapping("/register")
    public ModelAndView register(){
        return new ModelAndView("register");
    }

    @GetMapping("/houses")
    public ModelAndView houseList(){
        return new ModelAndView("hlist").addObject("n", dataService.count());
    }

    @GetMapping("/test")
    public ModelAndView test(){
        return new ModelAndView("test");
    }

    @GetMapping("/tt")
    @ResponseBody
    public String tt(){
        return restService.getRequest("https://postman-echo.com/get?foo1=bar1&foo2=bar2");
    }
}
