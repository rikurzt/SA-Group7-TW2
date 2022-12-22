package com.example.sa_g7_tw2_spring.Event;

import com.example.sa_g7_tw2_spring.DataAccessObject.ResultQueryDAO;
import com.example.sa_g7_tw2_spring.ValueObject.FindRequestVO;
import com.example.sa_g7_tw2_spring.ValueObject.ResultVO;
import com.example.sa_g7_tw2_spring.ValueObject.SendFindRequestResultVO;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.ParseException;
import java.util.Collection;


public class SpringRetrunByDate extends SpringEvent{

    private ResultQueryDAO resultQueryDAO;
    public SpringRetrunByDate(FindRequestVO vo){
        super(vo);

    }
    @Override
    public Collection<SendFindRequestResultVO> execute() throws ParseException {
        resultQueryDAO=ResultQueryDAO.getInstance();
        resultQueryDAO.setJdbcTemplate(jdbcTemplate);
        return resultQueryDAO.returnBYDate((FindRequestVO) vo);
    }
}
