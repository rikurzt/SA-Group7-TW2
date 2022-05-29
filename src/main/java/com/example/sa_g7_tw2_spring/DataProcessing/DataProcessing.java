package com.example.sa_g7_tw2_spring.DataProcessing;

import com.example.sa_g7_tw2_spring.utils.ScriptRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Arrays;


public class DataProcessing {
    int nonce = 0;
    public double[] ProcessData(MultipartFile file){
        byte rawData[];
        try {
            rawData = file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        nonce++;

        File voiceFile = new File("src/voice"+nonce+".wav");
        if(!voiceFile.exists()){
            try {
                voiceFile.createNewFile();
            } catch (IOException e) {
                return null;
            }
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(voiceFile);
            fos.write(rawData);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(fos != null)
                    fos.close();
            } catch (IOException e) { }
        }
        File processPythonFile = new File("src/py/data_processing.py");


        double[] result = ScriptRunner.runScript((BufferedReader br)->{
            try{

                String line, last = null;
                while((line = br.readLine()) != null){
                    last = line;
                    System.out.println(line);
                }
                return Arrays.stream(last.split(" ")).mapToDouble((s)->Double.valueOf(s)).toArray();
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }, "py",processPythonFile.getAbsolutePath(), voiceFile.getAbsolutePath());

        return result;
    }

    public double[] test(File voiceFile){
        File processPythonFile = new File("src/py/data_processing.py");

        double[] result = ScriptRunner.runScript((BufferedReader br)->{
            try{
                String line, last = null;


                while ((line = br.readLine()) != null){
                    last = line;
                    System.out.println(line);
                }
                return Arrays.stream(last.split(" ")).mapToDouble((s)->Double.valueOf(s)).toArray();
            }catch (Exception e){
                return null;
            }
        }, "py",processPythonFile.getAbsolutePath(), voiceFile.getAbsolutePath());

        return result;
    }
}