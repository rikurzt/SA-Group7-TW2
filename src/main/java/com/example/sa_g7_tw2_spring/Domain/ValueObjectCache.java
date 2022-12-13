package com.example.sa_g7_tw2_spring.Domain;

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
        ResultVO resultVO = new ResultVO(null,false,0,null);
        voMap.put("resultVO",resultVO);
        FindRequestVO findRequestVo = new FindRequestVO(null,null,null);
        voMap.put("findRequestVo",findRequestVo);
        LoginDataVO loginDataVO = new LoginDataVO(null,null);
        voMap.put("loginDataVO",loginDataVO);
        UploadVO uploadVO = new UploadVO(null,null,null);
        voMap.put("uploadVO",uploadVO);
        UserVO userVO = new UserVO(null,null,null,null,0,null,null,null,null,null);
        voMap.put("userVO",userVO);
        WristbandVO wristbandVO = new WristbandVO(null,null);
        voMap.put("wristbandVO",wristbandVO);


    }
}
