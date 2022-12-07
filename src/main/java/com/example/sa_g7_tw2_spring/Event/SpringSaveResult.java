package com.example.sa_g7_tw2_spring.Event;

import com.example.sa_g7_tw2_spring.DataAccessObject.ResultProcessDAO;
import com.example.sa_g7_tw2_spring.ValueObject.ResultVO;
import com.example.sa_g7_tw2_spring.ValueObject.UploadVO;
import com.example.sa_g7_tw2_spring.ValueObject.ValueObject;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.text.ParseException;

public class SpringSaveResult extends SpringEvent{
    ResultProcessDAO resultProcessDAO = new ResultProcessDAO();
    public SpringSaveResult(ValueObject vo){
        super(vo);

    }
    @Override
    public Object execute() throws ParseException, IOException {
        ResultVO resultVO =(ResultVO)vo;
        resultProcessDAO.getInstance();
        resultProcessDAO.setJdbcTemplate(jdbcTemplate);
        resultProcessDAO.saveResult(resultVO,resultVO.getID());
        return null;
    }
}
