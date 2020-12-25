package com.lvtn.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lvtn.exception.BadRequestException;
import com.lvtn.model.JsonObject;
import com.lvtn.service.DataService;
import com.lvtn.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/data")
public class DataController {
    @Autowired
    private DataService dataService;

    @Autowired
    private Utils utils;

    @GetMapping("/list/{id}/dht")
    @ResponseBody
    public String getDHTList(@PathVariable("id")int id){
        return utils.objectMapper(dataService.getDHTList(id));
    }

    @GetMapping("/list/{id}/msc")
    @ResponseBody
    public String getMiscList(@PathVariable("id")int id){
        return utils.objectMapper(dataService.getMiscList(id));
    }

    @GetMapping("/top/{id}/dht")
    @ResponseBody
    public String getTopDHT(@PathVariable("id")int id){
        return utils.objectMapper(dataService.getTopDHT(id));
    }

    @GetMapping("/top/{id}/msc")
    @ResponseBody
    public String getTopMisc(@PathVariable("id")int id){
        return utils.objectMapper(dataService.getTopMisc(id));
    }

    @PostMapping("/add")
    @ResponseBody
    public String add(@RequestBody String body){
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonObject object = mapper.readValue(body, JsonObject.class);
            boolean success = dataService.addSync(object);
            if(success){
                return mapper.writeValueAsString(dataService.getSD(object.room));
            }else{
                return "ERR";
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
            return "ERR";
        }
    }

    @PostMapping("/add2")
    @ResponseBody
    public String add2(@RequestBody String body){
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonObject object = mapper.readValue(body, JsonObject.class);
            boolean success = dataService.addASync(object);
            if(success){
                return mapper.writeValueAsString(dataService.getSD(object.room));
            }else{
                return "ERR";
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
            return "ERR";
        }
    }

    @GetMapping("/check")
    @ResponseBody
    public String ping(){
        return "OK";
    }

    @PostMapping("/resend")
    @ResponseBody
    public String recover(@RequestBody String body)throws BadRequestException {
        System.out.println(body);
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
