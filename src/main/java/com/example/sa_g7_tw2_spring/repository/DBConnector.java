package com.example.sa_g7_tw2_spring.repository;

import com.example.sa_g7_tw2_spring.Entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class DBConnector implements IRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /*要添加例外處理*/
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
                "VALUES (?,?,?)",result.getTime(),result.getResult(),result.getLength());
    }

    @Override
    public void CatchSoundFile(MultipartFile file) throws IOException {
        File convertFile = new File("/file"+file.getOriginalFilename());

        convertFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(convertFile);
        fout.write(file.getBytes());
        for(byte b : file.getBytes()){
            //System.out.print(Integer.toHexString(b));
        }
        System.out.println(convertFile.getAbsolutePath());
        jdbcTemplate.update("INSERT INTO analysisresult.voicefile(content) " +
                "VALUES (?)",file.getBytes());
        fout.close();
    }


}
