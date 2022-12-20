package com.example.sa_g7_tw2_spring.DataAccessObject;

import com.example.sa_g7_tw2_spring.ValueObject.ResultVO;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;


public class ResultProcessDAO extends DataAccessObject{
    private static ResultProcessDAO resultProcessDAO = new ResultProcessDAO();
    public ResultProcessDAO getInstance(){
        return  resultProcessDAO;
    }


    public void saveResult(ResultVO result, String id) {

        jdbcTemplate.update("INSERT INTO analysisresult.analysis(up_date, result, record_len,user_ID) " +
                "VALUES (?,?,?,?)",result.getTime(),result.getResult(),result.getLength(),result.getUser_ID());
    }


    public void SoundFileToDB(MultipartFile file, String userid,String anID) throws IOException {
        jdbcTemplate.update("INSERT INTO analysisresult.voicefile(vofile,W_Name,An_ID) " +
                "VALUES (?,?,?)",file.getBytes(),userid,anID);

    }
    public String GenerateAnID(){
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }
}
