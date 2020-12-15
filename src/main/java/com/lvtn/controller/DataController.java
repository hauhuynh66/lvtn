package com.lvtn.controller;

import com.lvtn.exception.BadRequestException;
import com.lvtn.model.DHT;
import com.lvtn.model.House;
import com.lvtn.model.Misc;
import com.lvtn.service.DataService;
import com.lvtn.util.Formatter;
import com.lvtn.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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
    public String add(@RequestParam(value = "room")int room,
                      @RequestParam(value = "temp")double temp,
                      @RequestParam(value = "humid")double humid,
                      @RequestParam(value = "light")double light,
                      @RequestParam(value = "smoke")double smoke,
                      @RequestParam(value = "time")String timeString)throws BadRequestException{
        House house = dataService.getHouse(room);
        if(house==null){
            throw new BadRequestException("Bad Request");
        }else{
            Date t = Formatter.parseTime(timeString);
            DHT dht = new DHT(temp, humid, t, house);
            Misc misc = new Misc(smoke, light, t, house);
            dataService.add(house, dht, misc);
            return "OK";
        }
    }
}
