package com.example.sa_g7_tw2_spring.utils;

import java.util.Iterator;
import java.util.function.Consumer;

public class ByteStorage implements Iterable<Byte>{

    private byte[]shapes;
    private int index;

    public ByteStorage(byte[] bytes){
        shapes = bytes;
    }

    public void addByte(java.lang.Byte name){
        int i = index++;
        shapes[i] = name;
    }

    public byte[] getBytes(){
        return shapes;
    }

    @Override
    public ByteIterator iterator() {
        return new ByteIterator(shapes);
    }

}
