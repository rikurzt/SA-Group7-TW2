package com.example.sa_g7_tw2_spring.Domain.Observer;

import com.example.sa_g7_tw2_spring.Domain.*;
import com.example.sa_g7_tw2_spring.Domain.Command_and_Facade.DataBaseManager;
import com.example.sa_g7_tw2_spring.Domain.Prototype.ValueObjectCache;
import com.example.sa_g7_tw2_spring.ValueObject.AnalyzedVO;
import com.example.sa_g7_tw2_spring.ValueObject.ResultVO;
import com.example.sa_g7_tw2_spring.data.NullObject.MDVP;
import com.example.sa_g7_tw2_spring.utils.CreateLocalFile;
import org.jaudiotagger.audio.wav.util.WavInfoReader;

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
import java.util.concurrent.ExecutionException;


public class AnalyzeThread extends Thread implements ObservableSubject {

    //region parameter

    private File file;
    private boolean isParkinson;
    private LocalDateTime fileTime;
    private double recordLength;
    private String id;


    //endregion

    //region class
    private ResultVO resultVO;
    private AIRunner aiRunner;
    private DataProcessing dataProcessing;
    private List<Observer> observers = new ArrayList<>();
    private AnalyzedVO vo;
    private DataBaseManager dbmgr;
    public String token;
    private int userID;
    //endregion
    public AnalyzeThread(DataBaseManager dataBaseManager, AnalyzedVO vo) throws IOException {
        file= CreateLocalFile.process(vo.getMultipartFile());
        id=vo.getWristbandName();
        dbmgr = dataBaseManager;
        this.vo = vo;
        token = vo.getToken();
        userID = vo.getUserID();

    }

    public synchronized void run(){
        aiRunner = new AIRunner();
        dataProcessing = new DataProcessing();
        try {
            MDVP processResult = dataProcessing.ProcessData(file);
            isParkinson = processResult.analyze(aiRunner);
            fileTime= ReadFileLastModifiedTime(file);
            recordLength = getWavInfo(file);
            resultVO = (ResultVO) ValueObjectCache.getValueObject("resultVO");
            resultVO.setWristbandName(id);
            resultVO.setResult(isParkinson);
            resultVO.setLength(recordLength);
            resultVO.setTime(fileTime);
            resultVO.setUser_ID(userID);
            resultVO.setAn_ID(vo.getAn_ID());
            dbmgr.save(resultVO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                notifyObservers();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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
    public void notifyObservers() throws ExecutionException, InterruptedException {
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
