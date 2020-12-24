package com.lvtn.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lvtn.exception.BadRequestException;
import com.lvtn.model.JsonObject;
import com.lvtn.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class MainController {

    @Autowired
    private DataService dataService;

    @GetMapping("/manage")
    public ModelAndView main(){
        return new ModelAndView("manager");
    }

    @GetMapping("/ping")
    @ResponseBody
    public String ping(){
        return "OK";
    }

    @PostMapping("/recover")
    @ResponseBody
    public String recover(@RequestBody String body)throws BadRequestException{
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<JsonObject> objects = objectMapper.readValue(body, new TypeReference<List<JsonObject>>(){});
            dataService.addAll(objects);
            return "OK";
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new BadRequestException("error");
        }
    }
}
