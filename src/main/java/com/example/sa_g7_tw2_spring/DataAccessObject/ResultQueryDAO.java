package com.example.sa_g7_tw2_spring.DataAccessObject;

import com.example.sa_g7_tw2_spring.ValueObject.FindRequestVO;
import com.example.sa_g7_tw2_spring.ValueObject.ResultVO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.stream.Collectors;


public class ResultQueryDAO extends DataAccessObject{

    private static ResultQueryDAO resultQueryDAO = new ResultQueryDAO();
    public ResultQueryDAO getInstance(){
        return  resultQueryDAO;
    }

    private Collection<ResultVO> resultList(String sql){
        return jdbcTemplate.queryForList(sql).stream().map(map->{
            return new ResultVO((LocalDateTime) map.get("up_date"),(Boolean)map.get("result"),(Double)map.get("record_len"),(String) map.get("An_ID"),0);
        }).collect(Collectors.toList());

    }
    public Collection<ResultVO> returnByToday(FindRequestVO findRequestVO) {
        LocalDateTime today = LocalDateTime.now();
        String sql="SELECT analysisresult.analysis.up_date,analysisresult.analysis.result,analysisresult.analysis.record_len " +
                "FROM analysisresult.analysis LEFT JOIN analysisresult.user " +
                "ON analysisresult.analysis.User_ID =analysisresult.user.User_ID WHERE up_date = " +
                "\""+DateTimeFormatter.ofPattern("yyyy-MM-dd").format(today)+"\" "+
                " AND Email_Account = "+"\""+findRequestVO.getAccount()+"\"";
        return resultList(sql);
    }
    public Collection<ResultVO> returnBYDate(FindRequestVO findRequestVO) throws ParseException {
        String sql="SELECT analysisresult.analysis.up_date,analysisresult.analysis.result,analysisresult.analysis.record_len " +
                "FROM analysisresult.analysis LEFT JOIN analysisresult.user " +
                "ON analysisresult.analysis.User_ID =analysisresult.user.User_ID WHERE up_date = " +
                "\""+findRequestVO.getMessage()+"\" "+
                " AND Email_Account = "+"\""+findRequestVO.getAccount()+"\"";
        System.out.println(sql);
        System.out.println(findRequestVO.getMessage());
        return resultList(sql);
    }

}
