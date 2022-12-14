package com.example.sa_g7_tw2_spring.DataAccessObject;

import com.example.sa_g7_tw2_spring.Domain.*;
import com.example.sa_g7_tw2_spring.ValueObject.LoginDataVO;
import com.example.sa_g7_tw2_spring.ValueObject.ResultVO;
import com.example.sa_g7_tw2_spring.ValueObject.UserVO;
import com.example.sa_g7_tw2_spring.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@Repository
public class UserDAO extends DataAccessObject {
    private MiddlewareAuth loginAuth = new InputLegalMiddleware().setNext(new UserExistMiddleware().setNext(new PasswordCorrectMiddleware()));
    private SqlFlyWeightFactory sqlFlyWeightFactory = new SqlFlyWeightFactory();

    //region singleton
    private static UserDAO userDAO = new UserDAO();
    public UserDAO getInstance(){
        return  userDAO;
    }
    //endregion


    private Collection<String> resultList(String sql){
        return jdbcTemplate.queryForList(sql).stream().map(map->{
            return new String(map.get("token").toString());
        }).collect(Collectors.toList());
    }

    class SqlFlyWeight {
        String sql;
        public SqlFlyWeight(String sql) {
            this.sql = sql;
        }
    }
    class SqlFlyWeightFactory {
        private static HashMap<String, SqlFlyWeight> sqlFlyWeightMap = new HashMap<>();
        public SqlFlyWeight getSqlFlyWeight(String sqlFlyWeightKey) {

            if (sqlFlyWeightMap.containsKey(sqlFlyWeightKey)) {
                System.out.println("Without create " + sqlFlyWeightKey + " sqlFlyWeight");
                return sqlFlyWeightMap.get(sqlFlyWeightKey);
            } else {
                SqlFlyWeight newSqlFlyWeight = create(sqlFlyWeightKey);
                sqlFlyWeightMap.put(sqlFlyWeightKey, newSqlFlyWeight);
                return newSqlFlyWeight;
            }
        }
        public SqlFlyWeight create(String key){
            switch(key){
                case "getUserInfoWithAccount" :
                    return new SqlFlyWeight("SELECT * FROM analysisresult.userinformation WHERE Account = ");
                case "getUserInfoWithID" :
                    return new SqlFlyWeight("SELECT * FROM analysisresult.userinformation WHERE ID = ");
                default : //可选
                    return null;
            }
        }
    }
    public boolean update(UserVO user) {
        SqlFlyWeight sqlFlyWeight = sqlFlyWeightFactory.getSqlFlyWeight("getUserInfoWithAccount");
        //String sql="SELECT * FROM analysisresult.userinformation WHERE Account = "+"\""+user.getAccount()+"\"";
        String sql=sqlFlyWeight.sql+"\""+user.getAccount()+"\"";
        System.out.println(user.getAccount());
        List<UserVO> userDataFromDB=jdbcTemplate.queryForList(sql).stream().map(map->{
            return new UserVO((String) map.get("Account"),(String)map.get("Username"),(String)map.get("Password"),(String) map.get("Gender"),0,null,null,null,null,null,null);
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
        SqlFlyWeight sqlFlyWeight = sqlFlyWeightFactory.getSqlFlyWeight("getUserInfoWithAccount");
        String sql=sqlFlyWeight.sql+"\""+loginData.getAccount()+"\"";
        UserVO userDataFromDB;
        UserVO uservo= (UserVO) ValueObjectCache.getValueObject("userVO");
        try {
            userDataFromDB=jdbcTemplate.queryForList(sql).stream().map(map->{
                //uservo = new UserVO((String) map.get("Account"),(String)map.get("Username"),(String)map.get("Password"),(String) map.get("Gender"),0,null,null,null,null,null);
                uservo.setAccount((String) map.get("Account"));
                uservo.setUserName((String)map.get("Username"));
                uservo.setPassword((String)map.get("Username"));
                uservo.setToken((String)map.get("Username"));
                uservo.setAge((Integer) map.get("Username"));
                uservo.setGender((String)map.get("Username"));
                uservo.setPhone((String)map.get("Username"));
                uservo.setFamilyID((String)map.get("Username"));
                uservo.setFamilyName((String)map.get("Username"));
                uservo.setFamilyPhone((String)map.get("Username"));
                return uservo;
            }).collect(Collectors.toList()).get(0);
        }catch (Exception e){
            uservo.setAccount(null);
            uservo.setPassword(null);
            uservo.setToken(null);
            uservo.setUserName(null);
            uservo.setAge(0);
            uservo.setGender(null);
            uservo.setPhone(null);
            uservo.setFamilyID(null);
            uservo.setFamilyName(null);
            uservo.setFamilyPhone(null);
            userDataFromDB = uservo;
        }
        boolean canLogin= loginAuth.auth(loginData,userDataFromDB);
        return canLogin;
    }
    public boolean newUser(UserVO user){
        String sql="INSERT INTO analysisresult.user(Email_Account, Name,gender,age,phone,address )"+"VALUES(?,?,?,?,?,?)";
        //String sql2="UPDATE analysisresult.emergency_contact SET ";
        String sql3="INSERT INTO analysisresult.account(Email_Account, Password,Token )"+"VALUES(?,?,?)";
        String sql4="INSERT INTO analysisresult.emergency_contact(Emergency_Contact_Phone,Emergency_Contact_Name,USER_ID )"+"VALUES(?,?,?)";
        String sql5="INSERT INTO analysisresult.emergency_contact(Emergency_Contact_Phone,Emergency_Contact_Name,EC_ID )"+"VALUES(?,?,?)";
        try {
            /*
            if(jdbcTemplate.queryForList("SELECT COL_LENGTH(Email_Account) FORM analysisresult.user ").get(0)==null){
                jdbcTemplate.execute("alter table user add column Email_Account varchar(319) null default ");
            }
            */
            /*
            int userIDinuser =jdbcTemplate.queryForList("SELECT User_ID FROM analysisresult.emergency_contact WHERE User_ID = "+"\""+user.getAccount()+"\"").stream().map(map->{ int a =(Integer) map.get("USER_ID");return a; }).collect(Collectors.toList()).get(0);
            System.out.println(userIDinuser);
            boolean userIDinEM =jdbcTemplate.queryForList("SELECT User_ID FROM analysisresult.emergency_contact WHERE User_ID = "+"\""+userIDinuser+"\"").get(0).isEmpty();
            if (userIDinEM) {
                jdbcTemplate.update(sql4,null,null,null);
            }*/

            jdbcTemplate.update(sql3
                    ,user.getAccount(),MD5.encoding(user.getPassword()),user.getToken());
            jdbcTemplate.update(sql5,user.getFamilyPhone(),user.getUserName(),user.getFamilyID());
            jdbcTemplate.update(sql
                    ,user.getAccount(),user.getUserName()
                    ,user.getGender(),user.getAge(),user.getPhone(),user.getAddress());

        }catch (Exception e){
            System.out.println(e);
            return false;
        }
        return true;
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