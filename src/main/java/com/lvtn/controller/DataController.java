package com.lvtn.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lvtn.model.House;
import com.lvtn.model.JsonObject;
import com.lvtn.service.DataService;
import com.lvtn.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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

    @PostMapping("/add/{id}")
    @ResponseBody
    public String add(@RequestBody String body, @PathVariable("id")int room_id){
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonObject object = mapper.readValue(body, JsonObject.class);
            boolean success = dataService.add(room_id, object);
            if(success){
                return "OK";
            }else{
                return "ERR";
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
            return "ERR";
        }
    }
}
