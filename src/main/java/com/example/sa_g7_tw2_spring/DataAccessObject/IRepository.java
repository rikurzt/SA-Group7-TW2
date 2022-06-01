package com.example.sa_g7_tw2_spring.DataAccessObject;

import com.example.sa_g7_tw2_spring.ValueObject.FindRequestVO;
import com.example.sa_g7_tw2_spring.ValueObject.ResultVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;


public interface IRepository{

    public Collection<ResultVO> returnAll();

    public Collection<ResultVO> returnByToday(FindRequestVO findRequestVO);

    public Collection<ResultVO> returnByID(int id);
    public Collection<ResultVO>returnBYDate(FindRequestVO findRequestVO) throws ParseException;



}
