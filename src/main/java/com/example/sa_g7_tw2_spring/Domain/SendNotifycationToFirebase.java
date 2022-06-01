package com.example.sa_g7_tw2_spring.Domain;

import com.example.sa_g7_tw2_spring.ValueObject.ResultVO;

import com.google.firebase.messaging.*;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

@Service
public class SendNotifycationToFirebase {


    public void send(ResultVO result) throws FirebaseMessagingException, ExecutionException, InterruptedException {
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
                .setToken("ecrWgHWUfUs:APA91bGVcTTiydeg5Oxhguxoi5gbpoQqxhIwFccbw4xd-3QyV4mwx6YAwlMM1i5CMAebEl6iEddeOuaItDQmmYHH6APlmimqHLrCyqn1QWPl-OE0tRVkR2YWkAALwuowypahJN4YwSrx")
                .build();

        FirebaseMessaging.getInstance().sendAsync(m).get();
    }

}
