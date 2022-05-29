package com.example.sa_g7_tw2_spring.DataProcessing;

import com.example.sa_g7_tw2_spring.Entity.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class MultiThreadHandler {
    private Result result ;
    public void ExcudeAnalyze(MultipartFile f) throws InterruptedException {
        AnalyzeTheard theard = new AnalyzeTheard(f);
        theard.start();
        theard.join();
        result=theard.getResult();
    }
    public Result ReturnResult(){
        return result;
    }
}
