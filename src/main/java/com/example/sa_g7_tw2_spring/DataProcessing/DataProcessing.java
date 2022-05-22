package com.example.sa_g7_tw2_spring.DataProcessing;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataProcessing {
    int nonce = 0;
    public double[] ProcessData(byte[] rawData){
        nonce++;

        File voiceFile = new File("voices","voice"+nonce+".mp4");
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

        double[] result = ScriptRunner.runScript((InputStream stream)->{
            var br = new BufferedReader(new InputStreamReader(stream));
            try {
                return Arrays.stream(br.readLine().split(" ")).mapToDouble((s)->Double.valueOf(s)).toArray();
            } catch (IOException e) { }
            return null;
        }, "raw_data_processing.py", voiceFile.getAbsolutePath());
        return result;
    }
}