package com.example.sa_g7_tw2_spring.Controller;

import com.example.sa_g7_tw2_spring.DataProcessing.AI;
import com.example.sa_g7_tw2_spring.DataProcessing.DataProcessing;
import com.example.sa_g7_tw2_spring.Entity.Result;
import com.example.sa_g7_tw2_spring.Wristband.*;
import com.example.sa_g7_tw2_spring.repository.DBConnector;
import com.example.sa_g7_tw2_spring.utils.Reflect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;


@RestController
@RequestMapping("/db")
public class CloudComputing {

    private AI aicom = new AI();
    @Autowired
    private DBConnector dbConnector ;
    @GetMapping("/Test")
    public boolean testConnect() {
        return  true;
    }
    @GetMapping("/findall")
    public Collection<Result> returnAll() {
        return  dbConnector.returnAll();
    }

        @GetMapping("/findByToday")
    public Collection<Result> returnByToday() {
        return dbConnector.returnByToday();
    }

    @GetMapping("/findByID/{id}")
    public Collection<Result> returnByID(@PathVariable("id") int id) {
        return dbConnector.returnByID(id);
    }

    @PostMapping("/save")
    public void saveResult(@RequestBody Result result) {
        System.out.print(result);
        dbConnector.saveResult(result);
    }

    //public static Stack<UploadFile> DataBuffer = new Stack<UploadFile>();

    public int getNonce(DataProcessing dataProcessing){
        return Reflect.get(dataProcessing, "nonce");
    }
}

