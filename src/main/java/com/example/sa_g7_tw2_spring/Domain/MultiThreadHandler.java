package com.example.sa_g7_tw2_spring.Domain;

import com.example.sa_g7_tw2_spring.ValueObject.ResultVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;


@Service
public class MultiThreadHandler {
    private boolean analyzeResult ;
    private LocalDateTime fileTime ;
    private double recordLength ;
    public void ExcudeAnalyze(MultipartFile f) throws InterruptedException {
        AnalyzeTheard theard = new AnalyzeTheard(f);
        theard.start();
        theard.join();
        analyzeResult=theard.getResult();
        fileTime=theard.getfileTime();
        recordLength=theard.getrecordLength();

    }

    public boolean ReturnResult(){
        return analyzeResult;
    }
    public LocalDateTime ReturnFileTime(){
        return fileTime;
    }
    public double ReturnRecordLength(){
        return recordLength;
    }
}
