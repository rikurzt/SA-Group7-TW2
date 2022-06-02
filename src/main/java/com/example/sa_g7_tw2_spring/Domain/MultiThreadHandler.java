package com.example.sa_g7_tw2_spring.Domain;

import com.example.sa_g7_tw2_spring.DataAccessObject.ResultProcessDAO;
import com.example.sa_g7_tw2_spring.ValueObject.ResultVO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;


@Service
public class MultiThreadHandler {

    @Resource
    JdbcTemplate jdbcTemplate;
    public void ExcudeAnalyze(File f,double id)  {


        AnalyzeTheard theard = new AnalyzeTheard(f,jdbcTemplate,id);
        theard.start();

    }

}
