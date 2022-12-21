package com.example.sa_g7_tw2_spring.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class CreateLocalFile {
    public static File process(MultipartFile file){
            byte rawData[];
            try {
                rawData = file.getBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            File voiceFile = new File("voicetemp/voice"+ UUID.randomUUID() +".wav");
            if(!voiceFile.exists()){
                try {
                    voiceFile.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
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
            return voiceFile.getAbsoluteFile();
    }
}
