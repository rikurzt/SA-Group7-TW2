package com.example.sa_g7_tw2_spring.DataAccessObject.Flyweight;

import com.example.sa_g7_tw2_spring.DataAccessObject.UserDAO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class SqlFlyWeightFactory {
    private static SqlFlyWeightFactory sqlFlyWeightFactory = new SqlFlyWeightFactory();
    public static SqlFlyWeightFactory getInstance(){
        return  sqlFlyWeightFactory;
    }
    private SqlFlyWeightFactory(){};
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
                return new SqlFlyWeight("SELECT * FROM analysisresult.account WHERE Email_Account = ");
            case "getUserInfoWithID" :
                return new SqlFlyWeight("SELECT * FROM analysisresult.account WHERE ID = ");
            case "getTokenByWristbandName":
                return new SqlFlyWeight("SELECT a.W_Name,analysisresult.account.Token FROM " +
                        "(SELECT analysisresult.user.User_ID,analysisresult.user.Email_Account,analysisresult.wristband.W_Name " +
                        "FROM analysisresult.user LEFT JOIN analysisresult.wristband on analysisresult.user.User_ID = analysisresult.wristband.User_ID) " +
                        "AS a LEFT JOIN analysisresult.account on a.Email_Account = analysisresult.account.Email_Account WHERE W_Name = ");
            case "getIDByWristbandName":
                return new SqlFlyWeight("SELECT a.W_Name,a.User_ID FROM " +
                        "(SELECT analysisresult.user.User_ID,analysisresult.user.Email_Account,analysisresult.wristband.W_Name " +
                        "FROM analysisresult.user LEFT JOIN analysisresult.wristband on analysisresult.user.User_ID = analysisresult.wristband.User_ID) " +
                        "AS a LEFT JOIN analysisresult.account on a.Email_Account = analysisresult.account.Email_Account WHERE W_Name = ");
            case "newUserSQL4":
                return new SqlFlyWeight("INSERT INTO analysisresult.user(Email_Account, Name,gender,age,phone,address )"+"VALUES(?,?,?,?,?,?)");
            case "newUserSQL1":
                return new SqlFlyWeight("INSERT INTO analysisresult.account(Email_Account, Password,Token )"+"VALUES(?,?,?)");
            case "newUserSQL2":
                return new SqlFlyWeight("INSERT INTO analysisresult.emergency_contact(Emergency_Contact_Phone,Emergency_Contact_Name,EC_ID,User_ID )"+"VALUES(?,?,?,?)");
            case "newUserSQL3":
                return  new SqlFlyWeight("INSERT INTO analysisresult.wristband(W_Name,User_ID)"+"VALUES(?,?)");
            case "returnresult":
                return new SqlFlyWeight("SELECT analysisresult.analysis.up_date,analysisresult.analysis.result,analysisresult.analysis.record_len " +
                        "FROM analysisresult.analysis LEFT JOIN analysisresult.user " +
                        "ON analysisresult.analysis.User_ID =analysisresult.user.User_ID WHERE Email_Account = ");
            case "appendToday":
                LocalDateTime today = LocalDateTime.now();
                return new SqlFlyWeight(" AND up_date = "+
                        "\""+ DateTimeFormatter.ofPattern("yyyy-MM-dd").format(today)+"\" ");
            case "appendDate":
                return new SqlFlyWeight(" AND up_date = ");
            case "saveResult":
                return new SqlFlyWeight("INSERT INTO analysisresult.analysis(up_date, result, record_len,user_ID,An_ID,percentage) " +
                        "VALUES (?,?,?,?,?,?)");
            case "soundFileToDB":
                return new SqlFlyWeight("INSERT INTO analysisresult.voicefile(vofile,W_Name,An_ID) " +
                        "VALUES (?,?,?)");
            case "getIDByaccount":
                return  new SqlFlyWeight("SELECT analysisresult.user.User_ID FROM analysisresult.user WHERE analysisresult.user.Email_Account = ");
            default : //可选
                return null;
        }
    }
}