package com.example.sa_g7_tw2_spring.Event;

import com.example.sa_g7_tw2_spring.DataAccessObject.ResultProcessDAO;
import com.example.sa_g7_tw2_spring.ValueObject.UploadVO;
import com.example.sa_g7_tw2_spring.ValueObject.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.text.ParseException;

public class SpringFileUpload extends SpringEvent{
    @Autowired
    private ResultProcessDAO resultProcessDAO;
    public SpringFileUpload(ValueObject vo){
        super(vo);

    }
    @Override
    public Object excute() throws ParseException, IOException {
        UploadVO uploadVO =(UploadVO)vo;
        resultProcessDAO.SoundFileToDB(uploadVO.getMultipartFile(), uploadVO.getId());
        return null;
    }
}
