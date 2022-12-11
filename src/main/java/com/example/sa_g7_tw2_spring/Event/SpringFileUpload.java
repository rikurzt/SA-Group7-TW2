package com.example.sa_g7_tw2_spring.Event;

import com.example.sa_g7_tw2_spring.DataAccessObject.ResultProcessDAO;
import com.example.sa_g7_tw2_spring.Domain.MultiThreadHandler;
import com.example.sa_g7_tw2_spring.ValueObject.UploadVO;
import com.example.sa_g7_tw2_spring.ValueObject.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Object execute() throws ParseException, IOException {
        UploadVO uploadVO =(UploadVO)vo;
        resultProcessDAO.setJdbcTemplate(jdbcTemplate);
        String anID = resultProcessDAO.GenerateAnID();
        resultProcessDAO.SoundFileToDB(uploadVO.getMultipartFile(), uploadVO.getId(),anID);
        multiThreadHandler.executeAnalyze(uploadVO);
        return null;
    }
}
