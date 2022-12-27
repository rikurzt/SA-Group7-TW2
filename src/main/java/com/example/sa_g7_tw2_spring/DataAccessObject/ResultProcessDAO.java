package com.example.sa_g7_tw2_spring.DataAccessObject;

import com.example.sa_g7_tw2_spring.DataAccessObject.Flyweight.SqlFlyWeightFactory;
import com.example.sa_g7_tw2_spring.ValueObject.ResultVO;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;


public class ResultProcessDAO extends DataAccessObject{


    private static ResultProcessDAO resultProcessDAO = new ResultProcessDAO();
    public static ResultProcessDAO getInstance(){
        return  resultProcessDAO;
    }
    private ResultProcessDAO(){};


    public void saveResult(ResultVO result) {
        String sql = sqlFlyWeightFactory.getSqlFlyWeight("saveResult").sql;
        jdbcTemplate.update(sql,result.getTime(),result.getResult(),result.getLength(),result.getUser_ID(),result.getAn_ID(),result.getPercentage());
    }


    public void SoundFileToDB(MultipartFile file, String userid,String anID) throws IOException {
        String sql = sqlFlyWeightFactory.getSqlFlyWeight("soundFileToDB").sql;
        jdbcTemplate.update(sql,file.getBytes(),userid,anID);
    }
    public String GenerateAnID(){
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }
}
