package com.example.sa_g7_tw2_spring.Event;

import com.example.sa_g7_tw2_spring.DataAccessObject.ResultQueryDAO;
import com.example.sa_g7_tw2_spring.ValueObject.FindRequestVO;
import com.example.sa_g7_tw2_spring.ValueObject.ResultVO;
import com.example.sa_g7_tw2_spring.ValueObject.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.ParseException;
import java.util.Collection;

public class SpringRetrunByToday extends SpringEvent{
    public SpringRetrunByToday(FindRequestVO vo, JdbcTemplate jdbcTemplate){
        super(vo,jdbcTemplate);

    }
    @Override
    public Collection<ResultVO> excute() throws ParseException {
        return ((ResultQueryDAO)dao).returnByToday((FindRequestVO) vo);
    }
}
