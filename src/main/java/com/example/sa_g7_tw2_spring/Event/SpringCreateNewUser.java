package com.example.sa_g7_tw2_spring.Event;

import com.example.sa_g7_tw2_spring.DataAccessObject.UserDAO;
import com.example.sa_g7_tw2_spring.ValueObject.UserVO;

import java.io.IOException;
import java.text.ParseException;

public class SpringCreateNewUser extends SpringEvent {
    UserDAO userDAO = new UserDAO();
    public SpringCreateNewUser(UserVO vo) {
        super(vo);
    }

    @Override
    public Object execute() throws ParseException, IOException {
        return null;
    }
}
