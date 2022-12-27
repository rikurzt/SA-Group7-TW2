package com.example.sa_g7_tw2_spring.DataAccessObject;

import com.example.sa_g7_tw2_spring.DataAccessObject.Flyweight.SqlFlyWeightFactory;
import com.example.sa_g7_tw2_spring.Domain.Prototype.ValueObjectCache;
import com.example.sa_g7_tw2_spring.ValueObject.FindRequestVO;
import com.example.sa_g7_tw2_spring.ValueObject.SendFindRequestResultVO;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


public class ResultQueryDAO extends DataAccessObject{

    private static ResultQueryDAO resultQueryDAO = new ResultQueryDAO();
    public static ResultQueryDAO getInstance(){
        return  resultQueryDAO;
    }
    private ResultQueryDAO(){};
    int time = 0;
    String name = "";
    Collection<SendFindRequestResultVO> temp;
    private Collection<SendFindRequestResultVO> resultList(String sql){
        return jdbcTemplate.queryForList(sql).stream().map(map->{
            SendFindRequestResultVO vo =(SendFindRequestResultVO)ValueObjectCache.getValueObject("SendFindRequestResultVO");
            vo.setDate(((LocalDateTime) map.get("up_date")).toString());
            vo.setResult((Boolean)map.get("result"));
            if(map.get("percentage")!= null){
                vo.setPercentage((Float)map.get("percentage"));
            }
           return vo;
            //return new SendFindRequestResultVO(((LocalDateTime) map.get("up_date")).toString(),(Boolean)map.get("result"));
        }).collect(Collectors.toList());

    }
    private boolean canQuery(String n){
        System.out.println(name);
        System.out.println(time);
        if(name.isEmpty()||time == 0){
            name = n;
            time++;
            return true;
        }
        else  if(name.equals(n)){
            if (time>=4){
                time=0;
                return true;
            }
            time++;
            return false;
        }
        name =n;
        return true;
    }
    public Collection<SendFindRequestResultVO> returnByAll(FindRequestVO findRequestVO){//如果相同名稱的該vo近期已經被創建過一次(十秒?)，那就不訪問資料庫淺複製回傳
        if(canQuery(findRequestVO.getAccount())){
            String sql = sqlFlyWeightFactory.getSqlFlyWeight("returnresult").sql+"\""+findRequestVO.getAccount()+"\"";
            System.out.println(sql);
            System.out.println(findRequestVO.getMessage());
            temp = new ArrayList<>(resultList(sql));
            return temp;
        }
        return temp;

    };

    public Collection<SendFindRequestResultVO> returnByToday(FindRequestVO findRequestVO) {
        String sql = sqlFlyWeightFactory.getSqlFlyWeight("returnresult").sql
                +"\""+findRequestVO.getAccount()+"\""
                + sqlFlyWeightFactory.getSqlFlyWeight("appendToday");

        return resultList(sql);
    }
    public Collection<SendFindRequestResultVO> returnBYDate(FindRequestVO findRequestVO) throws ParseException {
        String sql = sqlFlyWeightFactory.getSqlFlyWeight("returnresult").sql
                +"\""+findRequestVO.getAccount()+"\" "
                + sqlFlyWeightFactory.getSqlFlyWeight("appendToday")
                +"\""+findRequestVO.getMessage()+"\"";
        System.out.println(sql);
        System.out.println(findRequestVO.getMessage());
        return resultList(sql);
    }

}
