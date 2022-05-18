package main;
import java.io.File;
import java.util.*;

public class Wristband {

    MicroPhone mic = new MicroPhone();
    Battery battery = new Battery();
    public Stack<UploadFile> FakeSDCard = new Stack<UploadFile>();
    /*
    public String gettime(){
        return "2022/5/5";
    }
    */
    public void SaveVoiceToSDCArd(){
        FakeSDCard.push(new UploadFile(mic.GetDataDate(),mic
                .GetRawData()));
    }
    public void UploadData(){
        CloudComputing.DataBuffer.add(FakeSDCard.pop());
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
class UploadFile{
    private long uploadDate;
    private File RawMP3;
    public UploadFile(long date,File file){
        uploadDate=date;
        RawMP3=file;
    }
    public File getFileRaw(){
        return RawMP3;
    }
    public long getFileDate(){
        return uploadDate;
    }
}
