package main;

import java.io.*;
<<<<<<< Updated upstream
import java.time.LocalDateTime;
=======
import java.nio.ByteBuffer;
import java.nio.file.Files;
>>>>>>> Stashed changes
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataProcessing {
<<<<<<< Updated upstream
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
=======
    UploadFile uploadFile;
    byte[] voiceRawData ;
    boolean locked = false;
    List<Double> result;

    public boolean receiveRawData(byte[] rawData){

        try {
            voiceRawData = Files.readAllBytes(uploadFile.getFileRaw().toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(locked) return false;
        rawDataProcessing();
        return true;
    }

    public void rawDataProcessing(){
        File voiceFile = new File("voice.mp4");
>>>>>>> Stashed changes
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