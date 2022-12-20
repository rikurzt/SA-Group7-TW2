package com.example.sa_g7_tw2_spring.Domain;

import com.example.sa_g7_tw2_spring.DataAccessObject.ResultProcessDAO;
import com.example.sa_g7_tw2_spring.DataAccessObject.UserDAO;
import com.example.sa_g7_tw2_spring.ValueObject.AnalyzedVO;
import com.example.sa_g7_tw2_spring.ValueObject.UploadVO;
import com.example.sa_g7_tw2_spring.pattern.ObservableSubject;
import com.example.sa_g7_tw2_spring.pattern.Observer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;


@Service
public class MultiThreadHandler implements Observer {
    @Autowired
    private DataBaseManager dataBaseManager;
    private final AnalyzeThread inUsed[] = new AnalyzeThread[8];
    private final Queue<AnalyzeThread> queue = new LinkedList<>();

    public void executeAnalyze(AnalyzedVO vo) throws IOException {
        AnalyzeThread thread = new AnalyzeThread(dataBaseManager,vo);
        queue(thread);
    }

    public void queue(AnalyzeThread thread){
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
                    AnalyzeThread thread = queue.poll();
                    thread.attach(this);
                    inUsed[j] = thread;
                    thread.start();
                }
            }else {
                if (!queue.isEmpty()){
                    AnalyzeThread thread = queue.poll();
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
            if(subject.equals(inUsed[i])){
                update(i);
                return;
            }
        }
    }
}
