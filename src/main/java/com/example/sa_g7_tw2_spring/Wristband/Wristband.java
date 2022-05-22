package com.example.sa_g7_tw2_spring.Wristband;

import java.io.File;
import java.util.*;

public class Wristband {

    MicroPhone mic = new MicroPhone();
    Battery battery = new Battery();
    //UploadedFile uploadedFile;
    //public Stack<uploadedFile> FakeSDCard = new Stack<uploadedFile>();

    public void SaveVoiceToSDCArd(){
        //FakeSDCard.push(new UploadFile(mic.GetDataDate(),mic
               // .GetRawData()));
    }
    public void UploadData(){
        //CloudComputing.DataBuffer.add(FakeSDCard.pop());
    }

}
class MicroPhone {

    private File rawdata;
    private long FileDate ;
    boolean DetectVoice(){
        boolean IsSpeaking=true;
        return IsSpeaking;
    }
    void RecordVoice(boolean Spoke) {
        rawdata=new File("123.mp3");
        FileDate = rawdata.lastModified();

    }
    public File GetRawData(){
        return rawdata;
    }
    public long GetDataDate(){
        return FileDate;
    }
}
class Battery{
    private int Volune=100;
    public int getVolune(){
        return Volune;
    }
}

