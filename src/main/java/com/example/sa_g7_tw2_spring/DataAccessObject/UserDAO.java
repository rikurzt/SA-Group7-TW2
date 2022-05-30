package com.example.sa_g7_tw2_spring.DataAccessObject;

import com.example.sa_g7_tw2_spring.ValueObject.LoginDataVo;
import com.example.sa_g7_tw2_spring.ValueObject.ResultVO;
import com.example.sa_g7_tw2_spring.ValueObject.UserVO;
import com.example.sa_g7_tw2_spring.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Repository
public class UserDAO implements IUserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public void update(UserVO user) {

        jdbcTemplate.update("INSERT INTO analysisresult.userinformation(Account, Password, Username) " +
                "VALUES (?,?,?)",user.getAccount(), MD5.encoding(user.getPassword()),user.getUserName());
    }

    @Override
    public boolean canlogin(LoginDataVo loginData) {
        String sql="SELECT * FROM analysisresult.userinformation WHERE Account = "+"\""+loginData.getAccount()+"\"";
        System.out.println(sql);
        List<UserVO> userDataFromDB=jdbcTemplate.queryForList(sql).stream().map(map->{
            return new UserVO((String) map.get("Account"),(String)map.get("Username"),(String)map.get("Password"));
        }).collect(Collectors.toList());
        String pw=userDataFromDB.get(0).getPassword();
        System.out.println(MD5.encoding(loginData.getPassword())+"  "+pw);
        boolean canLogin= (MD5.encoding(loginData.getPassword()).equals(pw));
        return canLogin;
    }


}
