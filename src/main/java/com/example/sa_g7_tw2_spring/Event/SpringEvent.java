package com.example.sa_g7_tw2_spring.Event;

import com.example.sa_g7_tw2_spring.ValueObject.ValueObject;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.text.ParseException;

public abstract class SpringEvent<T> {
    ValueObject vo;
    JdbcTemplate jdbcTemplate;

    public SpringEvent(ValueObject vo){
        this.vo = vo;
    }
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
            this.jdbcTemplate = jdbcTemplate;

    }
    public abstract T execute() throws ParseException, IOException;

}
