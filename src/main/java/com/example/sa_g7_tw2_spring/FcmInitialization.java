package com.example.sa_g7_tw2_spring;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

@Configuration
public class FcmInitialization {

    @Bean
    void initFcm() throws IOException {
        FileInputStream serviceAccount =
                new FileInputStream("D:\\program\\SA-Group7-TW2\\src\\key\\sa-tw2-49ebb-firebase-adminsdk-8hv3f-f03ae462ec.json");
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);
    }
}
