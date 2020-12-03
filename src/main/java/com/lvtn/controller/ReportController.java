package com.lvtn.controller;

import com.lvtn.service.DataService;
import com.lvtn.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/house")
public class ReportController {
    @Autowired
    private DataService dataService;
    @Autowired
    private Utils utils;
    @GetMapping("/{id}/report/basic")
    public String basicReport(@PathVariable("id")int id){
        return utils.objectMapper(dataService.basicReport(id));
    }

    @GetMapping("/{id}/report/extra")
    public String extraReport(@PathVariable("id")int id){
        return utils.objectMapper(dataService.extraReport(id));
    }
}
