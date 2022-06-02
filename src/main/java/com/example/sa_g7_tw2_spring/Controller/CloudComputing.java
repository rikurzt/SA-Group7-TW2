package com.example.sa_g7_tw2_spring.Controller;

import com.example.sa_g7_tw2_spring.DataAccessObject.ResultProcessDAO;
import com.example.sa_g7_tw2_spring.DataAccessObject.UserDAO;
import com.example.sa_g7_tw2_spring.Domain.DataProcessing;
import com.example.sa_g7_tw2_spring.Domain.MultiThreadHandler;
import com.example.sa_g7_tw2_spring.Domain.SendNotifycationToFirebase;
import com.example.sa_g7_tw2_spring.ValueObject.FindRequestVO;
import com.example.sa_g7_tw2_spring.ValueObject.LoginDataVO;
import com.example.sa_g7_tw2_spring.ValueObject.ResultVO;
import com.example.sa_g7_tw2_spring.ValueObject.UserVO;
import com.example.sa_g7_tw2_spring.DataAccessObject.ResultQueryDAO;
import com.example.sa_g7_tw2_spring.utils.CreateLocalFile;
import com.example.sa_g7_tw2_spring.utils.Reflect;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping("/db")
public class CloudComputing {

    @Autowired
    private MultiThreadHandler multiThreadHandler;

    @Autowired
    private ResultQueryDAO resultQueryDAO;
    @Autowired
    private ResultProcessDAO resultProcessDAO;
    @Autowired
    private UserDAO userDAO;
    @GetMapping("/Test")
    public boolean testConnect() {
        return  true;
    }

        @GetMapping("/findByToday")
    public Collection<ResultVO> returnByToday(@RequestBody FindRequestVO findRequestVO) {
        return resultQueryDAO.returnByToday(findRequestVO);
    }
    @GetMapping("/findByDate")
    public Collection<ResultVO>returnByDate(@RequestBody FindRequestVO findRequestVO ) throws ParseException {
       return resultQueryDAO.returnBYDate(findRequestVO);
    }
    @RequestMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void FileUpload(@RequestParam("file") MultipartFile file,@RequestParam("id") Double id )throws IOException, InterruptedException, FirebaseMessagingException, ExecutionException {
        resultProcessDAO.SoundFileToDB(file,id);
        multiThreadHandler.ExcudeAnalyze(CreateLocalFile.process(file),id,userDAO,resultProcessDAO);
    }
    @GetMapping("/login")
    public boolean UserLogin(@RequestBody LoginDataVO loginData){
        boolean canlogin=userDAO.canlogin(loginData);
        return canlogin;

    }
    @GetMapping("/newuser")
    public boolean NewUser(@RequestBody UserVO user){

        return userDAO.update(user);
    }

}

