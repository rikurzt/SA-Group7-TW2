package com.example.sa_g7_tw2_spring.Domain;

import com.example.sa_g7_tw2_spring.Event.SpringEvent;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;

@Repository
public class DataBaseManager<T> {
    //command pattern Invoker
    //password:123456
    private Stack<SpringEvent> eventQueue = new Stack<>();
    public void AddCommand(SpringEvent event){
        eventQueue.add(event);
    }
    public Collection<T> execute() throws ParseException, IOException {
          return (Collection<T>) eventQueue.pop().excute();

    }
}
