package com.example.sa_g7_tw2_spring.DataProcessing;

import org.springframework.web.multipart.MultipartFile;

public class MultiTheardHandler extends Thread{

    private AI aicom;
    private DataProcessing dataProcessing;
    private MultipartFile file;

    public MultiTheardHandler(MultipartFile f) {
        file=f;
    }

    @Override
    public void run(){
        aicom= new AI();
        dataProcessing = new DataProcessing();
        double processResult[] =dataProcessing.ProcessData(file);
        boolean analyzeResult = aicom.Analyze(processResult);

    }
}
