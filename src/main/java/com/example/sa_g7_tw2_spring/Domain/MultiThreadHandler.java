package com.example.sa_g7_tw2_spring.Domain;

import com.example.sa_g7_tw2_spring.DataAccessObject.ResultProcessDAO;
import com.example.sa_g7_tw2_spring.DataAccessObject.UserDAO;
import com.example.sa_g7_tw2_spring.pattern.ObservableSubject;
import com.example.sa_g7_tw2_spring.pattern.Observer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.LinkedList;
import java.util.Queue;


@Service
public class MultiThreadHandler implements Observer {
    @Resource
    private JdbcTemplate jdbcTemplate;
    private final AnalyzeTheard inUsed[] = new AnalyzeTheard[8];
    private final Queue<AnalyzeTheard> queue = new LinkedList<>();

    public void executeAnalyze(File f, double id, UserDAO userDAO, ResultProcessDAO resultProcessDAO)  {
        AnalyzeTheard thread = new AnalyzeTheard(f,jdbcTemplate,id,userDAO,resultProcessDAO);
        queue(thread);
    }

    public void queue(AnalyzeTheard thread){
        synchronized (queue) {
            queue.add(thread);
        }
        update();
    }

    public void update(){
        update(-1);
    }


    public void update(int i) {
        synchronized (queue) {
            if(i == -1) {
                for(int j = 0; !queue.isEmpty() && j < inUsed.length; j++) {
                    if(inUsed[j] != null) {
                        continue;
                    }
                    AnalyzeTheard thread = queue.poll();
                    thread.attach(this);
                    inUsed[j] = thread;
                    thread.start();
                }
            }else {
                if (!queue.isEmpty()){
                    AnalyzeTheard thread = queue.poll();
                    thread.attach(this);
                    inUsed[i] = thread;
                    thread.start();
                }else {
                    inUsed[i] = null;
                }
            }
        }
    }

    @Override
    public void update(ObservableSubject subject) {
        for(int i = 0; i < inUsed.length; i++) {
            if(inUsed[i].equals(subject)){
                update(i);
                return;
            }
        }
    }
}
