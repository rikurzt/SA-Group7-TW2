package com.example.sa_g7_tw2_spring.utils.Iterator;



import java.util.Iterator;

public class ByteIterator implements Iterator<Byte>{

    private byte[] shapes;
    int pos;

    public ByteIterator(byte[]shapes){
        this.shapes = shapes;
    }
    @Override
    public boolean hasNext() {
        if(pos >= shapes.length)
            return false;
        return true;
    }

    @Override
    public Byte next() {
        return shapes[pos++];
    }

}

