package com.example.sa_g7_tw2_spring.DataAccessObject;

import com.example.sa_g7_tw2_spring.Controller.CloudComputing;
import com.example.sa_g7_tw2_spring.DataAccessObject.Flyweight.SqlFlyWeightFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public abstract  class DataAccessObject {
    JdbcTemplate jdbcTemplate;
    protected SqlFlyWeightFactory sqlFlyWeightFactory =SqlFlyWeightFactory.getInstance();
     public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


}
