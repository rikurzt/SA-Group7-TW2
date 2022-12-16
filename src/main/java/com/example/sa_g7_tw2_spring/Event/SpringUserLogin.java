package com.example.sa_g7_tw2_spring.Event;

import com.example.sa_g7_tw2_spring.DataAccessObject.ResultQueryDAO;
import com.example.sa_g7_tw2_spring.DataAccessObject.UserDAO;
import com.example.sa_g7_tw2_spring.ValueObject.FindRequestVO;
import com.example.sa_g7_tw2_spring.ValueObject.LoginDataVO;
import com.example.sa_g7_tw2_spring.ValueObject.ResultVO;
import com.example.sa_g7_tw2_spring.ValueObject.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.ParseException;
import java.util.Collection;

public class SpringUserLogin extends SpringEvent{
    UserDAO userDAO=new UserDAO();
    public SpringUserLogin(ValueObject vo){
        super(vo);

    }
    @Override
    public ResponseEntity execute() throws ParseException {
        userDAO.getInstance();
        userDAO.setJdbcTemplate(jdbcTemplate);
        return userDAO.canlogin((LoginDataVO) vo);
    }
}
