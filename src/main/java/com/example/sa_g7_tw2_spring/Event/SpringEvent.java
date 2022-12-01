package com.example.sa_g7_tw2_spring.Event;

import com.example.sa_g7_tw2_spring.DataAccessObject.DataAccessObject;
import com.example.sa_g7_tw2_spring.ValueObject.ResultVO;
import com.example.sa_g7_tw2_spring.ValueObject.ValueObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;

public abstract class SpringEvent<T> {
    ValueObject vo;
    public SpringEvent(ValueObject vo){
        this.vo = vo;
    }
    public abstract T excute() throws ParseException, IOException;

}
