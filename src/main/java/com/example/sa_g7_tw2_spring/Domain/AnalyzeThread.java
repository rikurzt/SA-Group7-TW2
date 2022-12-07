package com.example.sa_g7_tw2_spring.Domain;

import com.example.sa_g7_tw2_spring.DataAccessObject.ResultProcessDAO;
import com.example.sa_g7_tw2_spring.DataAccessObject.UserDAO;
import com.example.sa_g7_tw2_spring.Event.SpringSaveResult;
import com.example.sa_g7_tw2_spring.ValueObject.ResultVO;
import com.example.sa_g7_tw2_spring.ValueObject.UploadVO;
import com.example.sa_g7_tw2_spring.data.MDVP;
import com.example.sa_g7_tw2_spring.data.RealMDVP;
import com.example.sa_g7_tw2_spring.pattern.ObservableSubject;
import com.example.sa_g7_tw2_spring.pattern.Observer;
import org.jaudiotagger.audio.wav.util.WavInfoReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


public class AnalyzeThread extends Thread implements ObservableSubject {

    //region parameter

    private File file;
    private boolean isParkinson;
    private LocalDateTime fileTime;
    private double recordLength;
    private String id;

    //endregion

    //region class
    private AIRunner aiRunner;
    private DataProcessing dataProcessing;
    private SendNotifycationToFirebase sendNotifycationToFirebase=new SendNotifycationToFirebase();
    private List<Observer> observers = new ArrayList<>();
    private UploadVO vo;
    private DataBaseManager dbmgr;
    //endregion
    public AnalyzeThread(DataBaseManager dataBaseManager, UploadVO vo) throws IOException {
        file=vo.getMultipartFile().getResource().getFile();
        id=vo.getId();
        dbmgr = dataBaseManager;
        this.vo = vo;

    }

    public synchronized void run(){
        aiRunner = new AIRunner();
        dataProcessing = new DataProcessing();
        try {
            MDVP processResult = dataProcessing.ProcessData(file);
            isParkinson = processResult.analyze(aiRunner);
            fileTime= ReadFileLastModifiedTime(file);
            recordLength = getWavInfo(file);
            ResultVO resultVO =new ResultVO(fileTime, isParkinson,recordLength,id);
            dbmgr.AddCommand(new SpringSaveResult(resultVO));

            sendNotifycationToFirebase.send(resultVO,vo.getToken());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            notifyObservers();
        }

    }
    public  LocalDateTime ReadFileLastModifiedTime(File file) throws IOException {
        Path path = Paths.get(file.getAbsolutePath());
        BasicFileAttributes attributes = Files.readAttributes(path,BasicFileAttributes.class);
        LocalDateTime t = LocalDateTime.ofInstant(attributes.lastModifiedTime().toInstant(), ZoneId.systemDefault());
        return t;

    }
    public  double getWavInfo(File file) throws Exception {
        WavInfoReader wavInfoReader = new WavInfoReader();
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        // wav音频时长
        long duration = (long) (wavInfoReader.read(raf).getPreciseLength() * 1000);
        // wav音频采样率
        int sampleRate = toInt(read(raf, 24, 4));
        System.out.println("duration -> " + duration + ",sampleRate -> " + sampleRate);
        raf.close();
        return duration;
    }
    public  int toInt(byte[] b) {
        return ((b[3] << 24) + (b[2] << 16) + (b[1] << 8) + (b[0]));
    }

    public  byte[] read(RandomAccessFile rdf, int pos, int length) throws IOException {
        rdf.seek(pos);
        byte[] result = new byte[length];
        for (int i = 0; i < length; i++) {
            result[i] = rdf.readByte();
        }
        return result;
    }

    @Override
    public void notifyObservers() {
        for(Observer observer : observers) {
            observer.update(this);
        }
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }
}
