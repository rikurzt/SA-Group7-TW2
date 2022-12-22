package com.example.sa_g7_tw2_spring.DataAccessObject;

import com.example.sa_g7_tw2_spring.DataAccessObject.Flyweight.SqlFlyWeightFactory;
import com.example.sa_g7_tw2_spring.Domain.Prototype.ValueObjectCache;
import com.example.sa_g7_tw2_spring.ValueObject.FindRequestVO;
import com.example.sa_g7_tw2_spring.ValueObject.SendFindRequestResultVO;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.stream.Collectors;


public class ResultQueryDAO extends DataAccessObject{

    private static ResultQueryDAO resultQueryDAO = new ResultQueryDAO();
    private SqlFlyWeightFactory sqlFlyWeightFactory =SqlFlyWeightFactory.getInstance();
    public static ResultQueryDAO getInstance(){
        return  resultQueryDAO;
    }
    private ResultQueryDAO(){};

    private Collection<SendFindRequestResultVO> resultList(String sql){
        return jdbcTemplate.queryForList(sql).stream().map(map->{
            SendFindRequestResultVO vo =(SendFindRequestResultVO)ValueObjectCache.getValueObject("SendFindRequestResultVO");
            vo.setDate(((LocalDateTime) map.get("up_date")).toString());
            vo.setResult((Boolean)map.get("result"));
           return vo;
            //return new SendFindRequestResultVO(((LocalDateTime) map.get("up_date")).toString(),(Boolean)map.get("result"));
        }).collect(Collectors.toList());

    }
    public Collection<SendFindRequestResultVO> returnByAll(FindRequestVO findRequestVO){//returnresult
        String sql = sqlFlyWeightFactory.getSqlFlyWeight("returnresult").sql+"\""+findRequestVO.getAccount()+"\"";
        System.out.println(sql);
        System.out.println(findRequestVO.getMessage());
        return resultList(sql);

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
