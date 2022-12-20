package com.example.sa_g7_tw2_spring.Event;

import com.example.sa_g7_tw2_spring.DataAccessObject.ResultQueryDAO;
import com.example.sa_g7_tw2_spring.ValueObject.FindRequestVO;
import com.example.sa_g7_tw2_spring.ValueObject.ResultVO;
import com.example.sa_g7_tw2_spring.ValueObject.SendFindRequestResultVO;
import com.example.sa_g7_tw2_spring.ValueObject.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.ParseException;
import java.util.Collection;

public class SpringRetrunByToday extends SpringEvent{
    private ResultQueryDAO resultQueryDAO = new ResultQueryDAO();
    public SpringRetrunByToday(FindRequestVO vo){
        super(vo);

    }
    @Override
    public Collection<SendFindRequestResultVO> execute() throws ParseException {
        resultQueryDAO.getInstance();
        resultQueryDAO.setJdbcTemplate(jdbcTemplate);
        return resultQueryDAO.returnByToday((FindRequestVO) vo);
    }
}
