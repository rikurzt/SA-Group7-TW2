package com.example.sa_g7_tw2_spring.DataAccessObject;

import com.example.sa_g7_tw2_spring.ValueObject.LoginDataVO;
import com.example.sa_g7_tw2_spring.ValueObject.UserVO;
import com.example.sa_g7_tw2_spring.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;


@Repository
public class UserDAO implements IUserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public boolean update(UserVO user) {
        String sql="SELECT * FROM analysisresult.userinformation WHERE Account = "+"\""+user.getAccount()+"\"";
        List<UserVO> userDataFromDB=jdbcTemplate.queryForList(sql).stream().map(map->{
            return new UserVO((String) map.get("Account"),(String)map.get("Username"),(String)map.get("Password"),(String) map.get("Gender"),0,null,null,null,null);
        }).collect(Collectors.toList());

        if(userDataFromDB==null){
            jdbcTemplate.update("INSERT INTO analysisresult.userinformation(Account, Password, Username,gender,age,phone,familyID,familyName,familyPhone) " +
                    "VALUES (?,?,?,?,?,?,?,?,?)"
                    ,user.getAccount(), MD5.encoding(user.getPassword()),user.getUserName()
                    ,user.getGender(),user.getAge(),user.getPhone(),user.getFamilyID()
                    ,user.getFamilyName(),user.getFamilyPhone());
            return true;
        }
        return false;

    }

    @Override
    public boolean canlogin(LoginDataVO loginData) {
        String sql="SELECT * FROM analysisresult.userinformation WHERE Account = "+"\""+loginData.getAccount()+"\"";
        System.out.println(sql);
        List<UserVO> userDataFromDB=jdbcTemplate.queryForList(sql).stream().map(map->{
            return new UserVO((String) map.get("Account"),(String)map.get("Username"),(String)map.get("Password"),(String) map.get("Gender"),0,null,null,null,null);
        }).collect(Collectors.toList());
        String pw=userDataFromDB.get(0).getPassword();
        System.out.println(MD5.encoding(loginData.getPassword())+"  "+pw);
        boolean canLogin= (MD5.encoding(loginData.getPassword()).equals(pw));
        return canLogin;
    }


}
