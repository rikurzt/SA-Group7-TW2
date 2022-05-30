package com.example.sa_g7_tw2_spring.Domain;

import com.example.sa_g7_tw2_spring.ValueObject.ResultVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class MultiThreadHandler {
    private ResultVO result ;
    public void ExcudeAnalyze(MultipartFile f) throws InterruptedException {
        AnalyzeTheard theard = new AnalyzeTheard(f);
        theard.start();
        theard.join();
        result=theard.getResult();
    }
    public ResultVO ReturnResult(){
        return result;
    }
}
