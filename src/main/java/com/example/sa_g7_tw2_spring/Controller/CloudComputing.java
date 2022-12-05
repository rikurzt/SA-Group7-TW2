package com.example.sa_g7_tw2_spring.Controller;

import com.example.sa_g7_tw2_spring.DataAccessObject.ResultProcessDAO;
import com.example.sa_g7_tw2_spring.DataAccessObject.UserDAO;
import com.example.sa_g7_tw2_spring.Domain.DataBaseManager;
import com.example.sa_g7_tw2_spring.Domain.MultiThreadHandler;
import com.example.sa_g7_tw2_spring.Event.SpringFileUpload;
import com.example.sa_g7_tw2_spring.Event.SpringRetrunByDate;
import com.example.sa_g7_tw2_spring.Event.SpringRetrunByToday;
import com.example.sa_g7_tw2_spring.Event.SpringUserLogin;
import com.example.sa_g7_tw2_spring.ValueObject.*;
import com.example.sa_g7_tw2_spring.DataAccessObject.ResultQueryDAO;
import com.example.sa_g7_tw2_spring.utils.CreateLocalFile;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
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
    private JdbcTemplate jdbcTemplate;
    private UserDAO userDAO;


    private DataBaseManager dbMgr = new DataBaseManager();
    @GetMapping("/Test")
    public boolean testConnect() {
        return  true;
    }

    @GetMapping("/findByToday")
    public Collection<ResultVO> returnByToday(@RequestBody FindRequestVO findRequestVO) throws ParseException, IOException {
        dbMgr.AddCommand(new SpringRetrunByToday(findRequestVO,jdbcTemplate));
        return (Collection<ResultVO>) dbMgr.execute();
    }
    @GetMapping("/findByDate")
    public Collection<ResultVO>returnByDate(@RequestBody FindRequestVO findRequestVO ) throws ParseException, IOException {
        dbMgr.AddCommand(new SpringRetrunByDate(findRequestVO,jdbcTemplate));
       return (Collection<ResultVO>)dbMgr.execute();
    }
    @RequestMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void FileUpload(@RequestParam("file") MultipartFile file,@RequestParam("id") String id ) throws IOException, InterruptedException, FirebaseMessagingException, ExecutionException, ParseException {
        dbMgr.AddCommand(new SpringFileUpload((ValueObject) new UploadVO(file,id),jdbcTemplate));
        dbMgr.execute();
    }
    /*
    @RequestMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void FileUpload(@RequestParam("file") MultipartFile file,@RequestParam("id") Double id )throws IOException, InterruptedException, FirebaseMessagingException, ExecutionException {
        resultProcessDAO.SoundFileToDB(file,id);
        multiThreadHandler.executeAnalyze(CreateLocalFile.process(file),id,userDAO,resultProcessDAO);
    }*/
    @GetMapping("/login")
    public boolean UserLogin(@RequestBody LoginDataVO loginData) throws ParseException, IOException {
        dbMgr.AddCommand(new SpringUserLogin((ValueObject)loginData,jdbcTemplate));
        return (boolean)dbMgr.execute();

    }
    @GetMapping("/newuser")
    public boolean NewUser(@RequestBody UserVO user){
        return userDAO.update(user);
    }

}

