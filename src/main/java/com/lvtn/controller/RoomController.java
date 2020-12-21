package com.lvtn.controller;

import com.lvtn.exception.BadRequestException;
import com.lvtn.model.RS;
import com.lvtn.model.Room;
import com.lvtn.model.RoomDevice;
import com.lvtn.repository.RoomDeviceRepository;
import com.lvtn.service.DataService;
import com.lvtn.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/room")
public class RoomController {
    @Autowired
    private DataService dataService;
    @Autowired
    private RoomDeviceRepository roomDeviceRepository;

    @GetMapping("/{id}")
    public ModelAndView house(@PathVariable int id){
        Room room = dataService.getRoom(id);
        return new ModelAndView("main").addObject("room", room);
    }

    @GetMapping("/{id}/weather")
    public ModelAndView weather(@PathVariable int id){
        Room room = dataService.getRoom(id);
        return new ModelAndView("weather").addObject("room", room);
    }

    @GetMapping("/{id}/misc")
    public ModelAndView misc(@PathVariable int id){
        Room room = dataService.getRoom(id);
        return new ModelAndView("misc").addObject("room", room);
    }

    @GetMapping("/{id}/report")
    public ModelAndView report(@PathVariable int id){
        Room room = dataService.getRoom(id);
        ModelAndView report = new ModelAndView("report");
        report.addObject("room", room);
        return report;
    }
    @GetMapping("/{id}/setting")
    public ModelAndView setting(@PathVariable int id){
        Room room = dataService.getRoom(id);
        ModelAndView setting = new ModelAndView("setting").addObject("room", room);
        try {
            File file = new ClassPathResource("setting/"+ room.getId()+".json").getFile();
            RS rs = Utils.getFromFile(file);
            setting.addObject("rs", rs);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return setting;
    }

    @GetMapping("/{id}/setting/change")
    @ResponseBody
    public String change(@PathVariable("id") int id, @RequestBody String data){
        System.out.println(data);
        return "OK";
    }

    @GetMapping("/{id}/device")
    public ModelAndView device(@PathVariable("id") int id){
        Room room = dataService.getRoom(id);
        return new ModelAndView("device").addObject("room", room)
                .addObject("dvs", dataService.getDeviceList(id));
    }

    @GetMapping("/{id}/{nid}")
    @ResponseBody
    public String changeDeviceState(@PathVariable("id")int id,
                                    @PathVariable("nid")String nid) throws BadRequestException{
        if(dataService.changeState(id, nid)){
            return "OK";
        }else throw new BadRequestException("Device not exist");
    }
}
