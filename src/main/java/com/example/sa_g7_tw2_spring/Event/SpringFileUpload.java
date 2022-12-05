package com.example.sa_g7_tw2_spring.Event;

import com.example.sa_g7_tw2_spring.DataAccessObject.ResultProcessDAO;
import com.example.sa_g7_tw2_spring.ValueObject.UploadVO;
import com.example.sa_g7_tw2_spring.ValueObject.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.text.ParseException;

public class SpringFileUpload extends SpringEvent{

    public SpringFileUpload(ValueObject vo, JdbcTemplate jdbcTemplate){
        super(vo,jdbcTemplate);

    }
    @Override
    public Object excute() throws ParseException, IOException {
        UploadVO uploadVO =(UploadVO)vo;
        ((ResultProcessDAO)dao).SoundFileToDB(uploadVO.getMultipartFile(), uploadVO.getId());
        return null;
    }
}
