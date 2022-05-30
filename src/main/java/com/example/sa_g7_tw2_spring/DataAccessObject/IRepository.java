package com.example.sa_g7_tw2_spring.DataAccessObject;

import com.example.sa_g7_tw2_spring.ValueObject.ResultVO;
import com.example.sa_g7_tw2_spring.ValueObject.UserVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;


public interface IRepository{

    public Collection<ResultVO> returnAll();

    public Collection<ResultVO> returnByToday();

    public Collection<ResultVO> returnByID(int id);
    public Collection<ResultVO>returnBYDate(String date) throws ParseException;
    public void saveResult(ResultVO result);

    public void CatchSoundFile(MultipartFile file) throws IOException;


}
