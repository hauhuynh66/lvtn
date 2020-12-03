package com.lvtn.controller;

import com.lvtn.model.House;
import com.lvtn.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/house")
public class HouseController {
    @Autowired
    private DataService dataService;

    @GetMapping("/{id}")
    public ModelAndView house(@PathVariable int id){
        House house = dataService.getHouse(id);
        return new ModelAndView("main").addObject("house", house);
    }

    @GetMapping("/{id}/weather")
    public ModelAndView weather(@PathVariable int id){
        House house = dataService.getHouse(id);
        return new ModelAndView("weather").addObject("house", house);
    }

    @GetMapping("/{id}/misc")
    public ModelAndView misc(@PathVariable int id){
        House house = dataService.getHouse(id);
        return new ModelAndView("misc").addObject("house", house);
    }

    @GetMapping("/{id}/report")
    public ModelAndView report(@PathVariable int id){
        House house = dataService.getHouse(id);
        ModelAndView report = new ModelAndView("report");
        report.addObject("house", house);
        return report;
    }
}
