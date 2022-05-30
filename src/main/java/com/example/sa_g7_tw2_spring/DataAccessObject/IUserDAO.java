package com.example.sa_g7_tw2_spring.DataAccessObject;

import com.example.sa_g7_tw2_spring.ValueObject.LoginDataVo;
import com.example.sa_g7_tw2_spring.ValueObject.UserVO;

public interface IUserDAO {
    public void update(UserVO user);
    public boolean canlogin(LoginDataVo loginData);

}
