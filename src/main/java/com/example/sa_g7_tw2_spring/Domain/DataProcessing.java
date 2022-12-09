package com.example.sa_g7_tw2_spring.Domain;

import com.example.sa_g7_tw2_spring.data.MDVP;
import com.example.sa_g7_tw2_spring.data.NullMDVP;
import com.example.sa_g7_tw2_spring.data.RealMDVP;
import com.example.sa_g7_tw2_spring.utils.ScriptRunner;

import java.io.*;
import java.util.Arrays;


public class DataProcessing {
    int nonce = 0;
    public MDVP ProcessData(File voiceFile){

        File processPythonFile = new File("src/py/data_processing.py");

        MDVP result = ScriptRunner.runScript((BufferedReader br)->{
            try{

                String line, last = null;
                while((line = br.readLine()) != null){
                    last = line;
                    System.out.println(line);
                }
                return new RealMDVP(Arrays.stream(last.split(" ")).mapToDouble((s)->Double.valueOf(s)).toArray());
            }catch (Exception e){
                return new NullMDVP();
            }
        }, "py",processPythonFile.getAbsolutePath(), voiceFile.getAbsolutePath());

        return result;
    }
}