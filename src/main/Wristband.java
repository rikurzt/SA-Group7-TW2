package main;
import java.util.*;

public class Wristband {

    MincroPhone mic = new MincroPhone();
    Battery battery = new Battery();
    public Stack<int[]> FakeSDCard = new Stack<int[]>();
    public String gettime(){
        return "2022/5/5";
    }
    public void SaveVoiceToSDCArd(){
        FakeSDCard.push(mic.GetRawData());
    }
    public void UploadData(){
        CloudComputing.DataBuffer.add(FakeSDCard.pop());
    }
}
class MincroPhone{
    private int[] rawdata=new int[101];
    boolean DetectVoice(){
        boolean IsSpeaking=true;
        return IsSpeaking;
    }
    void RecordVoice(boolean Spoke) {
        Random rand = new Random();
        if(Spoke){
            for (int i = 0; i <= 100;i++){
                rawdata[i]= rand.nextInt(1);
            }
        }
    }
    public int[] GetRawData(){
        return rawdata;
    }
}
class Battery{
    private int Volune=100;
    public int getVolune(){
        return Volune;
    }
}
