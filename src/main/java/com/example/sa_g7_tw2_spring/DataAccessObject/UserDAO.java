package com.example.sa_g7_tw2_spring.DataAccessObject;

import com.example.sa_g7_tw2_spring.ValueObject.LoginDataVO;
import com.example.sa_g7_tw2_spring.ValueObject.ResultVO;
import com.example.sa_g7_tw2_spring.ValueObject.UserVO;
import com.example.sa_g7_tw2_spring.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Repository
public class UserDAO implements DataAccessObject{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Collection<String> resultList(String sql){
        return jdbcTemplate.queryForList(sql).stream().map(map->{
            return new String(map.get("token").toString());
        }).collect(Collectors.toList());

    }
    public boolean update(UserVO user) {
        String sql="SELECT * FROM analysisresult.userinformation WHERE Account = "+"\""+user.getAccount()+"\"";
        System.out.println(user.getAccount());
        List<UserVO> userDataFromDB=jdbcTemplate.queryForList(sql).stream().map(map->{
            UserVO userVO = new UserVO.userVOBuilder()
            .setAccount((String) map.get("Account")) //set Account
            .setUserName((String)map.get("Username")) //set UserName
            .setPassword((String)map.get("Password")) //set setPassword
            .setGender((String) map.get("Gender")) //set setGender
            .build(); //build
            return userVO;
        }).collect(Collectors.toList());

        if(userDataFromDB.size()<1){
            jdbcTemplate.update("INSERT INTO analysisresult.userinformation(Account, Password, Username,gender,age,phone,familyID,familyName,familyPhone,token) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?)"
                    ,user.getAccount(), MD5.encoding(user.getPassword()),user.getUserName()
                    ,user.getGender(),user.getAge(),user.getPhone(),user.getFamilyID()
                    ,user.getFamilyName(),user.getFamilyPhone(),user.getToken());
            return true;
        }else{
            return false;
        }


    }
    public String returnTokenByID(double i,JdbcTemplate jdbcTemplate) {
        String sql="SELECT * FROM analysisresult.userinformation WHERE ID = "+i;

        String token=jdbcTemplate.queryForList(sql).stream().map(map->{
            return new String(map.get("token").toString());
        }).collect(Collectors.toList()).get(0);
        return token;
    }

    public boolean canlogin(LoginDataVO loginData) {
        String sql="SELECT * FROM analysisresult.userinformation WHERE Account = "+"\""+loginData.getAccount()+"\"";
        System.out.println(sql);
        List<UserVO> userDataFromDB;
        String pw;
        try {
             userDataFromDB=jdbcTemplate.queryForList(sql).stream().map(map->{
                return new UserVO((String) map.get("Account"),(String)map.get("Username"),(String)map.get("Password"),(String) map.get("Gender"),0,null,null,null,null,null);
            }).collect(Collectors.toList());
             pw=userDataFromDB.get(0).getPassword();
        }catch (Exception e){
            return false;
        }
        System.out.println(MD5.encoding(loginData.getPassword())+"  "+pw);
        boolean canLogin= (MD5.encoding(loginData.getPassword()).equals(pw));
        return canLogin;
    }
    /*{
    "account":"test@gmail.com",
    "userName":"testName",
    "password": "testPassword",
    "gender": "male",
    "age":"19",
    "phone":"0900000000",
    "familyID":"1",
    "familyName":"1",
    "familyPhone":"0987878878",
    "token":"ecrWgHWUfUs:APA91bGVcTTiydeg5Oxhguxoi5gbpoQqxhIwFccbw4xd-3QyV4mwx6YAwlMM1i5CMAebEl6iEddeOuaItDQmmYHH6APlmimqHLrCyqn1QWPl-OE0tRVkR2YWkAALwuowypahJN4YwSrx"

}*/


}
