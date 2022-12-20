package com.example.sa_g7_tw2_spring.Controller;

import com.example.sa_g7_tw2_spring.Domain.DataBaseManager;
import com.example.sa_g7_tw2_spring.Domain.MultiThreadHandler;
import com.example.sa_g7_tw2_spring.ValueObject.*;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping("/db")
public class CloudComputing {

    //做Command pattern的reciver 將DAO分出去

    @Autowired
    private MultiThreadHandler mth;
    @Autowired
    private DataBaseManager dbMgr ;
    @GetMapping("/Test")
    public boolean testConnect() {
        return  true;
    }

    @GetMapping("/findByToday")
    public Collection<ResultVO> returnByToday(@RequestBody FindRequestVO findRequestVO) throws ParseException, IOException {
        return dbMgr.getToday(findRequestVO);
    }
    @GetMapping("/findByDate")
    public Collection<ResultVO>returnByDate(@RequestBody FindRequestVO findRequestVO) throws ParseException, IOException {
        return dbMgr.getByDate(findRequestVO);
    }
    @RequestMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> FileUpload(@RequestParam("file") MultipartFile file, @RequestParam("id") String wristbandName ) throws IOException, InterruptedException, FirebaseMessagingException, ExecutionException, ParseException {
        System.out.println("aaa");
        return dbMgr.upload(file, wristbandName , mth);
    }
    @GetMapping("/login")
    public ResponseEntity<?> UserLogin(@RequestBody LoginDataVO loginData) throws ParseException, IOException {
        return dbMgr.login(loginData);
    }
    @GetMapping("/newuser")
    public ResponseEntity<?> NewUser(@RequestBody NewUserVO newUserVO) throws ParseException, IOException {
        return dbMgr.newUser(newUserVO);
    }

}

