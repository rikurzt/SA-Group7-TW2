package com.example.sa_g7_tw2_spring.repository;

import com.example.sa_g7_tw2_spring.Entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.stream.Collectors;

@Repository
public class DBConnector implements IRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Collection<Result> resultList(String sql){
        return jdbcTemplate.queryForList(sql).stream().map(map->{
            return new Result((LocalDateTime) map.get("up_date"),(Boolean)map.get("result"),(Double)map.get("record_len"));
        }).collect(Collectors.toList());

    }

    @Override
    public Collection<Result> returnAll() {
        String sql="SELECT * FROM analysisresult.analysis";
        return resultList(sql);
    }

    @Override
    public Collection<Result> returnByToday() {
        LocalDateTime today = LocalDateTime.now();
        String sql="SELECT * FROM analysisresult.analysis WHERE DATE(up_date) =" +
                "\""+DateTimeFormatter.ofPattern("yyyy-MM-dd").format(today)+"\"";
        System.out.print(sql);
        return resultList(sql);
    }
    @Override
    public Collection<Result> returnByID(int i) {
        String sql="SELECT * FROM analysisresult.analysis WHERE ID = "+i;
        System.out.print(sql);
        return resultList(sql);


        /*
        return (Result) jdbcTemplate.queryForObject(sql,(r,id)->{
            return new Result(
                    LocalDateTime.of(r.getDate("up_date").toLocalDate()
                            ,r.getTime("up_date").toLocalTime())
                    , r.getBoolean("result"),r.getDouble("record_len"));
        },i);*/


    }

    @Override
    public void saveResult(Result result) {
        jdbcTemplate.update("INSERT INTO analysisresult.analysis(up_date, result, record_len) " +
                "VALUES (?,?,?)",result.getTime(),result.isResult(),result.getLength());
    }


}
