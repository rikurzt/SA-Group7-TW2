package com.example.sa_g7_tw2_spring.Domain;

import com.example.sa_g7_tw2_spring.DataAccessObject.ResultQueryDAO;
import com.example.sa_g7_tw2_spring.Event.SpringEvent;


import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;


public class DataBaseManager<T> {
    //command pattern Invoker
    //password:123456
    private Stack<SpringEvent> eventQueue = new Stack<>();
    private ResultQueryDAO resultQueryDAO =new ResultQueryDAO();
    public void AddCommand(SpringEvent event){
        event.setDAO(resultQueryDAO);
        eventQueue.add(event);
    }
    public T execute() throws ParseException, IOException {
          return (T) eventQueue.pop().excute();

    }
}
