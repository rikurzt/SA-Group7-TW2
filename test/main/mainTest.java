package main;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class mainTest {
    public Wristband wristband = new Wristband();
    public CloudComputing cloudComputing = new CloudComputing();

    @Test
    public void TestWristband(){
        Assertions.assertEquals(wristband.battery.getVolune(),100);
        wristband.mic.RecordVoice(wristband.mic.DetectVoice());
        Assertions.assertNotNull(wristband.mic.GetRawData());
        wristband.SaveVoiceToSDCArd();
        Assertions.assertNotNull(wristband.mic.GetRawData());
        wristband.SaveVoiceToSDCArd();
        Assertions.assertEquals(wristband.FakeSDCard.empty(),false);
        Assertions.assertEquals(wristband.FakeSDCard.empty(),false);
        wristband.FakeSDCard.pop();
        wristband.FakeSDCard.pop();
        Assertions.assertEquals(wristband.FakeSDCard.empty(),true);
    }
    @Test
    public void TestUploadFile(){
        wristband.mic.RecordVoice(wristband.mic.DetectVoice());
        wristband.SaveVoiceToSDCArd();
        wristband.UploadData();
        Assertions.assertEquals(CloudComputing.DataBuffer.isEmpty(),false);
    }
}