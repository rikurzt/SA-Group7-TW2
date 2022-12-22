package com.example.sa_g7_tw2_spring.DataAccessObject;

import com.example.sa_g7_tw2_spring.DataAccessObject.Flyweight.SqlFlyWeight;
import com.example.sa_g7_tw2_spring.DataAccessObject.Flyweight.SqlFlyWeightFactory;
import com.example.sa_g7_tw2_spring.Domain.Chain.InputLegalMiddleware;
import com.example.sa_g7_tw2_spring.Domain.Chain.MiddlewareAuth;
import com.example.sa_g7_tw2_spring.Domain.Chain.PasswordCorrectMiddleware;
import com.example.sa_g7_tw2_spring.Domain.Chain.UserExistMiddleware;
import com.example.sa_g7_tw2_spring.Domain.Prototype.ValueObjectCache;
import com.example.sa_g7_tw2_spring.ValueObject.*;
import com.example.sa_g7_tw2_spring.utils.Iterator.MD5;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.stream.Collectors;



public class UserDAO extends DataAccessObject {
    private MiddlewareAuth loginAuth = new InputLegalMiddleware().
            setNext(new UserExistMiddleware().
                    setNext(new PasswordCorrectMiddleware()));
    private SqlFlyWeightFactory sqlFlyWeightFactory = SqlFlyWeightFactory.getInstance() ;


    //region singleton
    private static UserDAO userDAO = new UserDAO();
    public static UserDAO getInstance(){
        return  userDAO;
    }
    private UserDAO(){};
    //endregion


    public String returnTokenByWristbandName(String name) {
        //先從wristband名字中抓使用者資料庫ID，再用ID找到email，用email關連到token
        SqlFlyWeight sqlFlyWeight = sqlFlyWeightFactory.getSqlFlyWeight("getTokenByWristbandName");
        String sql=sqlFlyWeight.sql+"\""+name+"\"";
        String token=jdbcTemplate.queryForList(sql).stream().map(map->{
            return new String(map.get("token").toString());
        }).collect(Collectors.toList()).get(0);
        return token;
    }
    public int returnIDByWristbandName(String name) {
        //先從wristband名字中抓使用者資料庫ID，再用ID找到email，用email關連到token
        SqlFlyWeight sqlFlyWeight = sqlFlyWeightFactory.getSqlFlyWeight("getIDByWristbandName");
        String sql=sqlFlyWeight.sql+"\""+name+"\"";
        int userID=jdbcTemplate.queryForList(sql).stream().map(map->{
            return new Integer(map.get("User_ID").toString());
        }).collect(Collectors.toList()).get(0);
        return userID;
    }

    public ResponseEntity canlogin(LoginDataVO loginData) {
        SqlFlyWeight sqlFlyWeight = sqlFlyWeightFactory.getSqlFlyWeight("getUserInfoWithAccount");
        String sql=sqlFlyWeight.sql+"\""+loginData.getAccount()+"\"";
        System.out.println(sql);
        AccountVO accountDataFromDB;
        AccountVO accountVO=(AccountVO) ValueObjectCache.getValueObject("AccountVO");
        try {
            accountDataFromDB=jdbcTemplate.queryForList(sql).stream().map(map->{
                accountVO.setAccount((String)map.get("Email_Account"));
                accountVO.setPassword((String)map.get("Password"));
                accountVO.setToken((String)map.get("Token") );
                return accountVO;
            }).collect(Collectors.toList()).get(0);
            boolean canLogin= loginAuth.auth(loginData,accountDataFromDB);
            if(canLogin){
                return new ResponseEntity("Login Success", HttpStatus.OK);
            }else{
                return new ResponseEntity("Login Error!", HttpStatus.FORBIDDEN);
            }

        }catch (Exception e){
            return new ResponseEntity("Login Error!"+e, HttpStatus.FORBIDDEN);
        }


    }
    public ResponseEntity newUser(NewUserVO user){
        String sql1=sqlFlyWeightFactory.getSqlFlyWeight("newUserSQL1").sql;
        String sql2=sqlFlyWeightFactory.getSqlFlyWeight("newUserSQL2").sql;
        String sql3=sqlFlyWeightFactory.getSqlFlyWeight("newUserSQL3").sql;
        String sql4=sqlFlyWeightFactory.getSqlFlyWeight("newUserSQL4").sql;
        try {
            jdbcTemplate.update(sql1
                    ,user.getAccount(),MD5.encoding(user.getPassword()),user.getToken());
            jdbcTemplate.update(sql2,user.getFamilyPhone(),user.getUserName(),user.getFamilyID());
            jdbcTemplate.update(sql3,user.getWristbandName());
            jdbcTemplate.update(sql4
                    ,user.getAccount(),user.getUserName()
                    ,user.getGender(),user.getAge(),user.getPhone(),user.getAddress());

        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("create error"+e, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("create Success", HttpStatus.OK);
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