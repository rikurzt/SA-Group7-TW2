package com.example.sa_g7_tw2_spring.Event;

import com.example.sa_g7_tw2_spring.DataAccessObject.UserDAO;
import com.example.sa_g7_tw2_spring.ValueObject.NewUserVO;
import com.example.sa_g7_tw2_spring.ValueObject.UserVO;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.text.ParseException;

public class SpringCreateNewUser extends SpringEvent {
    private UserDAO userDAO = new UserDAO();
    public SpringCreateNewUser(NewUserVO vo) {
        super(vo);
    }

    @Override
    public ResponseEntity execute() throws ParseException, IOException {
        userDAO.getInstance();
        userDAO.setJdbcTemplate(jdbcTemplate);
        return userDAO.newUser((NewUserVO) vo);
    }
}
