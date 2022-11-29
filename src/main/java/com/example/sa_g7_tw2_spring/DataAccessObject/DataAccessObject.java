package com.example.sa_g7_tw2_spring.DataAccessObject;

import com.example.sa_g7_tw2_spring.Controller.CloudComputing;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class DataAccessObject {

    @Autowired
    CloudComputing cloudComputing;
    public abstract void execute();



}
