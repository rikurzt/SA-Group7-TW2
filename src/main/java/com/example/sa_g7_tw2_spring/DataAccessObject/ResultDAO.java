package com.example.sa_g7_tw2_spring.DataAccessObject;

import com.example.sa_g7_tw2_spring.ValueObject.FindRequestVO;
import com.example.sa_g7_tw2_spring.ValueObject.ResultVO;
import com.example.sa_g7_tw2_spring.ValueObject.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.stream.Collectors;

@Repository
public class ResultDAO implements IRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private Collection<ResultVO> resultList(String sql){
        return jdbcTemplate.queryForList(sql).stream().map(map->{
            return new ResultVO((LocalDateTime) map.get("up_date"),(Boolean)map.get("result"),(Double)map.get("record_len"),(String)map.get("wistband_ID"));
        }).collect(Collectors.toList());

    }

    @Override
    public Collection<ResultVO> returnAll() {
        String sql="SELECT * FROM analysisresult.analysis";
        return resultList(sql);
    }

    @Override
    public Collection<ResultVO> returnByToday(FindRequestVO findRequestVO) {
        LocalDateTime today = LocalDateTime.now();
        String sql="SELECT * FROM analysisresult.analysis WHERE DATE(up_date) =" +
                "\""+DateTimeFormatter.ofPattern("yyyy-MM-dd").format(today)+"\""+" WHERE Account = "+"\""+findRequestVO.getAccount()+"\"";
        System.out.print(sql);
        return resultList(sql);
    }
    @Override
    public Collection<ResultVO> returnByID(int i) {
        String sql="SELECT * FROM analysisresult.analysis WHERE ID = "+i;
        System.out.print(sql);
        return resultList(sql);
    }

    @Override
    public Collection<ResultVO> returnBYDate(FindRequestVO findRequestVO) throws ParseException {
        String sql="SELECT * FROM analysisresult.analysis WHERE DATE(up_date) =" +
                "\""+new SimpleDateFormat("yyyy-MM-dd").parse(findRequestVO.getMessage()) +"\" "+
                " WHERE Account = "+"\""+findRequestVO.getAccount()+"\"";
        System.out.print(sql);
        return resultList(sql);
    }

    @Override
    public void saveResult(ResultVO result) {
        jdbcTemplate.update("INSERT INTO analysisresult.analysis(up_date, result, record_len,wristband_ID) " +
                "VALUES (?,?,?,?)",result.getTime(),result.getResult(),result.getLength(),result.getWristbandID());
    }

    @Override
    public void SoundFileToDB(MultipartFile file) throws IOException {
        jdbcTemplate.update("INSERT INTO analysisresult.voicefile(content) " +
                "VALUES (?)",file.getBytes());

    }



}
