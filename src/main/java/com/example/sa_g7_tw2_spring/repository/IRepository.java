package com.example.sa_g7_tw2_spring.repository;

import com.example.sa_g7_tw2_spring.Entity.Result;

import java.time.LocalDateTime;
import java.util.Collection;


public interface IRepository{

    public Collection<Result> returnAll();

    public Collection<Result> returnByToday();

    public Collection<Result> returnByID(int id);
    public void saveResult(Result result);


}
