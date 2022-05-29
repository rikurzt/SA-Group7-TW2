package com.example.sa_g7_tw2_spring.Controller;

import com.example.sa_g7_tw2_spring.DataProcessing.DataProcessing;
import com.example.sa_g7_tw2_spring.DataProcessing.AnalyzeTheard;
import com.example.sa_g7_tw2_spring.DataProcessing.MultiThreadHandler;
import com.example.sa_g7_tw2_spring.Entity.Result;
import com.example.sa_g7_tw2_spring.repository.DBConnector;
import com.example.sa_g7_tw2_spring.utils.Reflect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.*;


@RestController
@RequestMapping("/db")
public class CloudComputing {

    @Autowired
    private MultiThreadHandler multiThreadHandler;

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

    @RequestMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void FileUpload(@RequestParam("file") MultipartFile file) throws IOException, InterruptedException {
        dbConnector.CatchSoundFile(file);
        multiThreadHandler.ExcudeAnalyze(file);
        dbConnector.saveResult(multiThreadHandler.ReturnResult());

    }

    //public static Stack<UploadFile> DataBuffer = new Stack<UploadFile>();

    public int getNonce(Class<?> clazz, DataProcessing dataProcessing){
        return Reflect.get(dataProcessing, "nonce");
    }
}

