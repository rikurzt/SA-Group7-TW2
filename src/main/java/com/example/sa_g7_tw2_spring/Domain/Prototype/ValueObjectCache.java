package com.example.sa_g7_tw2_spring.Domain.Prototype;

import com.example.sa_g7_tw2_spring.ValueObject.*;

import java.awt.*;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Hashtable;

public class ValueObjectCache {
    private static Hashtable<String, ValueObject> voMap = new Hashtable<>();

    public static ValueObject getValueObject(String voName){
        ValueObject cacheVO = voMap.get(voName);
        return (ValueObject) cacheVO.clone();
    }
    public static void loadCache(){
        ResultVO resultVO = new ResultVO(null,false,0,null,0,null,0);
        voMap.put("resultVO",resultVO);
        FindRequestVO findRequestVo = new FindRequestVO(null,null);
        voMap.put("findRequestVO",findRequestVo);
        LoginDataVO loginDataVO = new LoginDataVO(null,null);
        voMap.put("loginDataVO",loginDataVO);
        UploadVO uploadVO = new UploadVO(null,null);
        voMap.put("uploadVO",uploadVO);
        UserVO userVO = new UserVO(null,null,null,0,null,null,null);
        voMap.put("userVO",userVO);
        AccountVO accountVO = new AccountVO(null,null,null);
        voMap.put("AccountVO",accountVO);
        EmergencyContectVO emergencyContectVO = new EmergencyContectVO(null,null,null);
        voMap.put("EmergencyContectVO",emergencyContectVO);
        WristbandVO wristbandVO = new WristbandVO(null);
        voMap.put("wristbandVO",wristbandVO);
        AnalyzedVO analyzedVO = new AnalyzedVO(null,null,null,0,null);
        voMap.put("AnalyzedVO",analyzedVO);
        SendFindRequestResultVO sendFindRequestResultVO = new SendFindRequestResultVO(null,false,0);
        voMap.put("SendFindRequestResultVO",sendFindRequestResultVO);
        NewUserVO newUserVO = new NewUserVO(null,null,null,null,0,null,null,null,null,null,null,null);
        voMap.put("newUserVO",newUserVO);
    }
}
