package com.example.sa_g7_tw2_spring.Domain;

import com.example.sa_g7_tw2_spring.Event.*;
import com.example.sa_g7_tw2_spring.ValueObject.*;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Stack;
import java.util.concurrent.ExecutionException;


@Repository
public class DataBaseManager {
    //command pattern Invoker
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private Stack<SpringEvent> eventQueue = new Stack<>();
    public void AddCommand(SpringEvent event){
        event.setJdbcTemplate(jdbcTemplate);
        eventQueue.add(event);
    }
    public <T> T execute() throws ParseException, IOException {
        LocalDateTime today = LocalDateTime.now();
        SpringEvent event =eventQueue.pop();
        System.out.println("["+DateTimeFormatter.ofPattern("yyyy-MM-dd HH:MM:ss").format(today)+"]"+event.getClass().getName()+" executed");
        try {
            return (T)event.execute();
        }catch (Exception e){
            System.out.println("["+DateTimeFormatter.ofPattern("yyyy-MM-dd HH:MM:ss").format(today)+"]"+event.getClass().getName()+" unexecuted"+"\n"+e);
            return null;
        }

    }

    public Collection<SendFindRequestResultVO> getToday(FindRequestVO findRequestVO) throws ParseException, IOException {
        AddCommand(new SpringRetrunByToday(findRequestVO));
        return execute();
    }

    public Collection<SendFindRequestResultVO> getByDate(FindRequestVO findRequestVO ) throws ParseException, IOException {
        AddCommand(new SpringRetrunByDate(findRequestVO));
        return execute();
    }

    public ResponseEntity<?> upload(MultipartFile file, String name, MultiThreadHandler mth) throws IOException, InterruptedException, FirebaseMessagingException, ExecutionException, ParseException {
        AddCommand(new SpringFileUploadAnalyze(new UploadVO(file,name),mth));
        return execute();

    }

    public ResponseEntity<?> login(LoginDataVO loginData) throws ParseException, IOException {
        AddCommand(new SpringUserLogin(loginData));
        return execute();
    }

    public void save(ValueObject vo) throws ParseException, IOException {
        AddCommand(new SpringSaveResult(vo));
        execute();
    }

    public ResponseEntity<?> newUser(NewUserVO vo) throws ParseException, IOException {
        AddCommand(new SpringCreateNewUser(vo));
        return execute();
    }

    public Collection<SendFindRequestResultVO> returnAll(FindRequestVO vo) throws ParseException, IOException {
        AddCommand(new SpringReturnAll(vo));
        return execute();
    }
}
