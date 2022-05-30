package com.example.sa_g7_tw2_spring.DataAccessObject;

import com.example.sa_g7_tw2_spring.ValueObject.LoginDataVO;
import com.example.sa_g7_tw2_spring.ValueObject.UserVO;

public interface IUserDAO {
    public boolean update(UserVO user);
    public boolean canlogin(LoginDataVO loginData);

}
