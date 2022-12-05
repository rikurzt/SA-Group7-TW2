package com.example.sa_g7_tw2_spring.Event;

import com.example.sa_g7_tw2_spring.DataAccessObject.DataAccessObject;
import com.example.sa_g7_tw2_spring.ValueObject.ResultVO;
import com.example.sa_g7_tw2_spring.ValueObject.ValueObject;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;

public abstract class SpringEvent<T> {
    ValueObject vo;
    JdbcTemplate jdbcTemplate;
    DataAccessObject dao;
    public SpringEvent(ValueObject vo,JdbcTemplate jdbcTemplate){
        this.vo = vo;
        this.jdbcTemplate=jdbcTemplate;
    }
    public void setDAO(DataAccessObject dataAccessObject){
        dao=dataAccessObject;
        dao.setJdbcTemplate(jdbcTemplate);

    }
    public abstract T excute() throws ParseException, IOException;

}
