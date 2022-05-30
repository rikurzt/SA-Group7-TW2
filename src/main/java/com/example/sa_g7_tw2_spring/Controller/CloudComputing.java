package com.example.sa_g7_tw2_spring.Controller;

import com.example.sa_g7_tw2_spring.DataAccessObject.UserDAO;
import com.example.sa_g7_tw2_spring.Domain.DataProcessing;
import com.example.sa_g7_tw2_spring.Domain.MultiThreadHandler;
import com.example.sa_g7_tw2_spring.Domain.SendNotifycationToFirebase;
import com.example.sa_g7_tw2_spring.ValueObject.LoginDataVO;
import com.example.sa_g7_tw2_spring.ValueObject.ResultVO;
import com.example.sa_g7_tw2_spring.ValueObject.UserVO;
import com.example.sa_g7_tw2_spring.DataAccessObject.ResultDAO;
import com.example.sa_g7_tw2_spring.utils.Reflect;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.*;


@RestController
@RequestMapping("/db")
public class CloudComputing {

    @Autowired
    private MultiThreadHandler multiThreadHandler;

    @Autowired
    private ResultDAO resultDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private SendNotifycationToFirebase sendNotifycationToFirebase;
    @GetMapping("/Test")
    public boolean testConnect() {
        return  true;
    }
    @GetMapping("/findall")
    public Collection<ResultVO> returnAll() {
        return  resultDAO.returnAll();
    }

        @GetMapping("/findByToday")
    public Collection<ResultVO> returnByToday() {
        return resultDAO.returnByToday();
    }

    @GetMapping("/findByID/{id}")
    public Collection<ResultVO> returnByID(@PathVariable("id") int id) {
        return resultDAO.returnByID(id);
    }

    @PostMapping("/save")
    public void saveResult(@RequestBody ResultVO result) {

        resultDAO.saveResult(result);
    }

    @RequestMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void FileUpload(@RequestParam("file") MultipartFile file) throws IOException, InterruptedException, FirebaseMessagingException {
        resultDAO.CatchSoundFile(file);
        multiThreadHandler.ExcudeAnalyze(file);
        ResultVO finalResult = multiThreadHandler.ReturnResult();
        resultDAO.saveResult(finalResult);
        sendNotifycationToFirebase.send(finalResult);

    }

    @GetMapping("/login")
    public boolean UserLogin(@RequestBody LoginDataVO loginData){
        boolean canlogin=userDAO.canlogin(loginData);
        return canlogin;

    }
    @PostMapping("/newuser")
    public void NewUser(@RequestBody UserVO user){

        userDAO.update(user);

    }

    //public static Stack<UploadFile> DataBuffer = new Stack<UploadFile>();

    public int getNonce(Class<?> clazz, DataProcessing dataProcessing){
        return Reflect.get(dataProcessing, "nonce");
    }
}

