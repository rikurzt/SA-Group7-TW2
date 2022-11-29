package com.example.sa_g7_tw2_spring.Domain;

import com.example.sa_g7_tw2_spring.DataAccessObject.ResultProcessDAO;
import com.example.sa_g7_tw2_spring.DataAccessObject.UserDAO;
import com.example.sa_g7_tw2_spring.ValueObject.ResultVO;
import com.example.sa_g7_tw2_spring.ValueObject.UserVO;
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
    private JdbcTemplate jdbcTemplate;
    public void ExcudeAnalyze(File f, double id, UserDAO userDAO,ResultProcessDAO resultProcessDAO)  {

        AnalyzeTheard theard = new AnalyzeTheard(f,jdbcTemplate,id,userDAO,resultProcessDAO);
        theard.start();

    }

}
