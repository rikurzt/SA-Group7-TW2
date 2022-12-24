package com.example.sa_g7_tw2_spring.Domain.Observer_and_ThreadPool;

import com.google.firebase.messaging.*;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

@Service
public class SendNotifycationToFirebase implements Observer{


    @Override
    public void update(ObservableSubject o) throws  ExecutionException, InterruptedException {
        String token = null;
        if (o instanceof AnalyzeThread thread) {
            token =  thread.token;
        }
        AndroidConfig androidConfig =AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis())
                .setCollapseKey("topic")
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder().setSound("default").setColor("#ffffff").build())
                .build();
        ApnsConfig apnsConfig = ApnsConfig.builder().setAps(Aps.builder().setCategory("topic").setThreadId("topic").build()).build();
        Message m= Message.builder()
                .setApnsConfig(apnsConfig)
                .setAndroidConfig(androidConfig)
                .setNotification(Notification.builder()
                        .setBody("Analyze is Done")
                        .setTitle("Parkinson Diesase Notification").build())
                .setToken(token)
                .build();

        FirebaseMessaging.getInstance().sendAsync(m).get();
        System.out.println("sent");
    }

}
