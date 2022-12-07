package com.example.sa_g7_tw2_spring.DataAccessObject;

import com.example.sa_g7_tw2_spring.ValueObject.ResultVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public class ResultProcessDAO extends DataAccessObject{
    private static ResultProcessDAO resultProcessDAO = new ResultProcessDAO();
    public ResultProcessDAO getInstance(){
        return  resultProcessDAO;
    }


    public void saveResult(ResultVO result, String id) {

        jdbcTemplate.update("INSERT INTO analysisresult.analysis(up_date, result, record_len,userID) " +
                "VALUES (?,?,?,?)",result.getTime(),result.getResult(),result.getLength(),result.getID());
    }


    public void SoundFileToDB(MultipartFile file, String userid) throws IOException {
        jdbcTemplate.update("INSERT INTO analysisresult.voicefile(vofile,user_ID) " +
                "VALUES (?,?)",file.getBytes(),userid);

    }
}
