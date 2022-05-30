package com.example.sa_g7_tw2_spring.Domain;

import com.example.sa_g7_tw2_spring.ValueObject.ResultVO;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
public class SendNotifycationToFirebase {


    public void send(ResultVO result) throws FirebaseMessagingException {
        val message = Message.builder()
                .setNotification(Notification.builder()
                        .setTitle("From Wayne's Talk")
                        .setBody("Hello Wayns's Talk")
                        .build())
                .putData("upload_date", result.getTime().toString())
                .putData("is_parksion", String.valueOf(result.getResult()))
                .putData("length", String.valueOf(result.getLength()))
                .setToken("")
                .build();
        FirebaseMessaging.getInstance().send(message);
    }
}
