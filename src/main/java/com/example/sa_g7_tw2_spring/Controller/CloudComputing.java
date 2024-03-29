package com.example.sa_g7_tw2_spring.Controller;

import com.example.sa_g7_tw2_spring.Domain.Command_and_Facade.DataBaseManager;
import com.example.sa_g7_tw2_spring.Domain.Observer_and_ThreadPool.MultiThreadHandler;
import com.example.sa_g7_tw2_spring.Domain.Prototype.ValueObjectCache;
import com.example.sa_g7_tw2_spring.ValueObject.*;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
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
        System.out.println("testConnect");
        return  true;
    }
    @GetMapping("/findByToday")
    public Collection<SendFindRequestResultVO> returnByToday(@RequestBody FindRequestVO findRequestVO) throws ParseException, IOException {
        return dbMgr.getToday(findRequestVO);
    }
    @GetMapping("/findByDate")
    public Collection<SendFindRequestResultVO>returnByDate(@RequestBody FindRequestVO findRequestVO) throws ParseException, IOException {
        return dbMgr.getByDate(findRequestVO);
    }
    @RequestMapping(value ="/findAll",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Collection<SendFindRequestResultVO> returnAll(@RequestParam Map<String,String> params) throws ParseException, IOException {

        return dbMgr.returnAll(new FindRequestVO(params.get("account"),null));
    }
    @RequestMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> FileUpload(@RequestParam("file") MultipartFile file, @RequestParam("id") String wristbandName ) throws IOException, InterruptedException, FirebaseMessagingException, ExecutionException, ParseException {
        return dbMgr.upload(file, wristbandName , mth);
    }
    @RequestMapping(value ="/login",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> UserLogin(@RequestParam("account") String account,@RequestParam("password") String password) throws ParseException, IOException {
        LoginDataVO loginData = (LoginDataVO) ValueObjectCache.getValueObject("loginDataVO");
        loginData.setAccount(account);
        loginData.setPassword(password);
        return dbMgr.login(loginData);
    }
    @RequestMapping(value ="/newuser",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> NewUser(@RequestParam("account")String account,@RequestParam("userName")String userName,@RequestParam("password")String password,@RequestParam("gender")String gender,@RequestParam("age")String age,@RequestParam("phone")String phone,@RequestParam("address")String address,@RequestParam("familyID")String familyID,@RequestParam("familyName")String familyName,@RequestParam("familyPhone")String familyPhone,@RequestParam("token")String token,@RequestParam("wristbandName")String wristbandName  ) throws ParseException, IOException {
        NewUserVO newUserVO = (NewUserVO) ValueObjectCache.getValueObject("newUserVO");
        newUserVO.setAccount(account);
        newUserVO.setUserName(userName);
        newUserVO.setPassword(password);
        newUserVO.setGender(gender);
        newUserVO.setAge(Integer.parseInt(age));
        newUserVO.setAddress(address);
        newUserVO.setPhone(phone);
        newUserVO.setFamilyID(familyID);
        newUserVO.setFamilyName(familyName);
        newUserVO.setFamilyPhone(familyPhone);
        newUserVO.setToken(token);
        newUserVO.setWristbandName(wristbandName);
        return dbMgr.newUser(newUserVO);
    }

}

