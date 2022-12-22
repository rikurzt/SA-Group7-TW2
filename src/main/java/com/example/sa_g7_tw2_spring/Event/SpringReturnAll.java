package com.example.sa_g7_tw2_spring.Event;

import com.example.sa_g7_tw2_spring.DataAccessObject.ResultQueryDAO;
import com.example.sa_g7_tw2_spring.ValueObject.FindRequestVO;

import java.io.IOException;
import java.text.ParseException;

public class SpringReturnAll extends SpringEvent {

    private ResultQueryDAO resultQueryDAO;
    public SpringReturnAll(FindRequestVO vo) {
        super(vo);
    }

    @Override
    public Object execute() throws ParseException, IOException {
        resultQueryDAO=ResultQueryDAO.getInstance();
        resultQueryDAO.setJdbcTemplate(jdbcTemplate);
        return resultQueryDAO.returnByAll((FindRequestVO) vo);
    }
}
