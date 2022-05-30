package com.example.sa_g7_tw2_spring.Domain;

import com.example.sa_g7_tw2_spring.ValueObject.ResultVO;
import com.example.sa_g7_tw2_spring.utils.ReadFileInstanceTime;
import com.example.sa_g7_tw2_spring.utils.ReadRecordLength;
import org.springframework.web.multipart.MultipartFile;

public class AnalyzeTheard extends Thread{

    private AI aicom;
    private DataProcessing dataProcessing;
    private MultipartFile file;
    private ResultVO result;

    public AnalyzeTheard(MultipartFile f) {
        file=f;
    }

    @Override
    public synchronized void run(){
        aicom= new AI();
        dataProcessing = new DataProcessing();
        double processResult[] =dataProcessing.ProcessData(file);
        boolean analyzeResult = aicom.Analyze(processResult);

        try {
            result=new ResultVO(ReadFileInstanceTime.process(),analyzeResult,ReadRecordLength.getWavInfo());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public ResultVO getResult(){
        return result;
    }
}
