package com.lvtn.controller;

import com.lvtn.model.House;
import com.lvtn.model.RS;
import com.lvtn.service.DataService;
import com.lvtn.service.RestService;
import com.lvtn.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;

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
    @GetMapping("/{id}/setting")
    public ModelAndView setting(@PathVariable int id){
        House house = dataService.getHouse(id);
        ModelAndView setting = new ModelAndView("setting").addObject("house", house);
        try {
            File file = new ClassPathResource("setting/"+house.id+".json").getFile();
            RS rs = Utils.getFromFile(file);
            setting.addObject("rs", rs);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return setting;
    }
}
