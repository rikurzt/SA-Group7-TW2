package com.example.sa_g7_tw2_spring.Domain;

import com.example.sa_g7_tw2_spring.Event.*;
import com.example.sa_g7_tw2_spring.ValueObject.*;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Stack;
import java.util.concurrent.ExecutionException;


@Repository
public class DataBaseManager {
    //command pattern Invoker
    //password:123456
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private Stack<SpringEvent> eventQueue = new Stack<>();
    public void AddCommand(SpringEvent event) throws ParseException, IOException {
        event.setJdbcTemplate(jdbcTemplate);
        eventQueue.add(event);
        if(!eventQueue.isEmpty()){
            this.execute();
        }

    }
    public <T> T execute() throws ParseException, IOException {
          return (T) eventQueue.pop().execute();
    }

    public Collection<ResultVO> getToday(FindRequestVO findRequestVO) throws ParseException, IOException {
        AddCommand(new SpringRetrunByToday(findRequestVO));
        return execute();
    }

    public Collection<ResultVO> getByDate(FindRequestVO findRequestVO ) throws ParseException, IOException {
        AddCommand(new SpringRetrunByDate(findRequestVO));
        return execute();
    }

    public ResponseEntity<?> upload(MultipartFile file, String id, String token, MultiThreadHandler mth) throws IOException, InterruptedException, FirebaseMessagingException, ExecutionException, ParseException {
        AddCommand(new SpringFileUpload(new UploadVO(file,id,token),mth));
        return execute();

    }

    public boolean login(LoginDataVO loginData) throws ParseException, IOException {
        AddCommand(new SpringUserLogin(loginData));
        return execute();
    }

    public void save(ValueObject vo) throws ParseException, IOException {
        AddCommand(new SpringSaveResult(vo));
        execute();
    }
}
