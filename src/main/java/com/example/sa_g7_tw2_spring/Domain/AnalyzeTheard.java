package com.example.sa_g7_tw2_spring.Domain;

import com.example.sa_g7_tw2_spring.DataAccessObject.ResultProcessDAO;
import com.example.sa_g7_tw2_spring.ValueObject.ResultVO;
import com.example.sa_g7_tw2_spring.utils.ReadFileInstanceTime;
import com.example.sa_g7_tw2_spring.utils.ReadRecordLength;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;


public class AnalyzeTheard extends Thread{

    private AI aicom;
    private DataProcessing dataProcessing;
    private File file;
    private boolean analyzeResult;
    private LocalDateTime fileTime;
    private double recordLength;
    private SendNotifycationToFirebase sendNotifycationToFirebase= new SendNotifycationToFirebase();
    private JdbcTemplate jdbcTemplate;
    public AnalyzeTheard(File f, JdbcTemplate jdbcTemplate) {
        file=f;
        this.jdbcTemplate=jdbcTemplate;
    }

    @Resource
    private ResultProcessDAO resultProcessDAO = new ResultProcessDAO();

    @Override
    public synchronized void run(){
        aicom= new AI();
        dataProcessing = new DataProcessing();
        try {
            double processResult[] =dataProcessing.ProcessData(file);
            analyzeResult = aicom.Analyze(processResult);
            fileTime= ReadFileInstanceTime.process();
            recordLength = ReadRecordLength.getWavInfo();
            ResultVO resultVO =new ResultVO(fileTime,analyzeResult,recordLength,"");

            resultProcessDAO.saveResult(resultVO,jdbcTemplate);
            sendNotifycationToFirebase.send(resultVO);

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
