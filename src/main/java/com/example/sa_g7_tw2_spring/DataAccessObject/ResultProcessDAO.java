package com.example.sa_g7_tw2_spring.DataAccessObject;

import com.example.sa_g7_tw2_spring.ValueObject.ResultVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Repository
public class ResultProcessDAO implements DataAccessObject{
    @Autowired
    private JdbcTemplate jdbcTemplate ;
    public void saveResult(ResultVO result, JdbcTemplate jdbcTemplate, double id) {

        jdbcTemplate.update("INSERT INTO analysisresult.analysis(up_date, result, record_len,userID) " +
                "VALUES (?,?,?,?)",result.getTime(),result.getResult(),result.getLength(),result.getID());
    }


    public void SoundFileToDB(MultipartFile file, String userid) throws IOException {
        jdbcTemplate.update("INSERT INTO analysisresult.voicefile(content,userID) " +
                "VALUES (?,?)",file.getBytes(),userid);

    }
}
