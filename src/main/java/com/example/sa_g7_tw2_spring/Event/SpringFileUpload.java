package com.example.sa_g7_tw2_spring.Event;

import com.example.sa_g7_tw2_spring.DataAccessObject.ResultProcessDAO;
import com.example.sa_g7_tw2_spring.Domain.MultiThreadHandler;
import com.example.sa_g7_tw2_spring.ValueObject.UploadVO;
import com.example.sa_g7_tw2_spring.ValueObject.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.text.ParseException;

public class SpringFileUpload extends SpringEvent{
    ResultProcessDAO resultProcessDAO = new ResultProcessDAO();
    MultiThreadHandler multiThreadHandler;
    public SpringFileUpload(ValueObject vo, MultiThreadHandler mth){
        super(vo);
        multiThreadHandler = mth;
    }
    @Override
    public ResponseEntity execute() throws ParseException, IOException {
        UploadVO uploadVO =(UploadVO)vo;
        resultProcessDAO.setJdbcTemplate(jdbcTemplate);
        String anID = resultProcessDAO.GenerateAnID();
        try{
            resultProcessDAO.SoundFileToDB(uploadVO.getMultipartFile(), uploadVO.getId(),anID);

        }catch (Exception e){
            return new ResponseEntity("uploaded Error!"+e, HttpStatus.BAD_REQUEST);

        }
        multiThreadHandler.executeAnalyze(uploadVO);
        return new ResponseEntity("Successfully uploaded!", HttpStatus.OK);
    }
}
