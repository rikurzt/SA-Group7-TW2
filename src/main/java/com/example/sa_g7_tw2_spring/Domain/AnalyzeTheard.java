package com.example.sa_g7_tw2_spring.Domain;

import com.example.sa_g7_tw2_spring.ValueObject.ResultVO;
import com.example.sa_g7_tw2_spring.utils.ReadFileInstanceTime;
import com.example.sa_g7_tw2_spring.utils.ReadRecordLength;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

public class AnalyzeTheard extends Thread{

    private AI aicom;
    private DataProcessing dataProcessing;
    private MultipartFile file;
    private boolean analyzeResult;
    private LocalDateTime fileTime;
    private double recordLength;

    public AnalyzeTheard(MultipartFile f) {
        file=f;
    }

    @Override
    public synchronized void run(){
        aicom= new AI();
        dataProcessing = new DataProcessing();
        double processResult[] =dataProcessing.ProcessData(file);
        try {
            analyzeResult = aicom.Analyze(processResult);
            fileTime= ReadFileInstanceTime.process();
            recordLength = ReadRecordLength.getWavInfo();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public boolean getResult(){
        return analyzeResult;
    }
    public LocalDateTime getfileTime(){
        return fileTime;
    }
    public Double getrecordLength(){
        return recordLength;
    }
}
