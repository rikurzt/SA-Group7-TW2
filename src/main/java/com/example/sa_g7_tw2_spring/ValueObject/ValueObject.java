package com.example.sa_g7_tw2_spring.ValueObject;

public abstract class ValueObject <T> implements Cloneable {
    public Object clone(){
        Object clone = null;
        try {
            clone =super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }

}
