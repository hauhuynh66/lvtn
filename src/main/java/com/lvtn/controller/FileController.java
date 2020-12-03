package com.lvtn.controller;

import com.lvtn.service.DataService;
import com.lvtn.service.FileStorageService;
import com.lvtn.util.Excel;
import com.lvtn.util.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController {
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private DataService dataService;
    @Autowired
    private Excel excel;
    @PostMapping("/upload")
    public String upload(MultipartFile file){
        fileStorageService.store(file);
        return "OK";
    }

    @GetMapping("/excel")
    @ResponseBody
    public String create(){
        Report rp = dataService.basicReport(1);
        excel.writeToExcel(rp);
        return "OK";
    }
}
