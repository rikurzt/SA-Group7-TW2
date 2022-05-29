package com.example.sa_g7_tw2_spring.utils;

import org.jaudiotagger.audio.wav.util.WavInfoReader;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ReadRecordLength {

    public static double getWavInfo() throws Exception {
        File file = new File("src/voice1");
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
    public static int toInt(byte[] b) {
        return ((b[3] << 24) + (b[2] << 16) + (b[1] << 8) + (b[0]));
    }

    public static byte[] read(RandomAccessFile rdf, int pos, int length) throws IOException {
        rdf.seek(pos);
        byte[] result = new byte[length];
        for (int i = 0; i < length; i++) {
            result[i] = rdf.readByte();
        }
        return result;
    }

}
