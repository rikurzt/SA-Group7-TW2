package com.example.sa_g7_tw2_spring.repository;

import com.example.sa_g7_tw2_spring.Entity.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;


public interface IRepository{

    public Collection<Result> returnAll();

    public Collection<Result> returnByToday();

    public Collection<Result> returnByID(int id);
    public void saveResult(Result result);

    public void CatchSoundFile(MultipartFile file) throws IOException;


}
