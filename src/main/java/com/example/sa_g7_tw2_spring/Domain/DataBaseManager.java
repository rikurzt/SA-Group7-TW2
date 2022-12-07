package com.example.sa_g7_tw2_spring.Domain;

import com.example.sa_g7_tw2_spring.Event.SpringEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


import java.io.IOException;
import java.text.ParseException;
import java.util.Stack;


@Repository
public class DataBaseManager<T> {
    //command pattern Invoker
    //password:123456
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private Stack<SpringEvent> eventQueue = new Stack<>();
    public void AddCommand(SpringEvent event) throws ParseException, IOException {
        event.setJdbcTemplate(jdbcTemplate);
        eventQueue.add(event);
        if(!eventQueue.isEmpty()){
            this.execute();
        }

    }
    public T execute() throws ParseException, IOException {
          return (T) eventQueue.pop().execute();

    }
}
