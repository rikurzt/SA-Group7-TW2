package com.example.sa_g7_tw2_spring.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
@AllArgsConstructor
public class AnalyzedVO extends ValueObject{

    private MultipartFile multipartFile;
    private String wristbandName;
    private String token;
    private int userID;
    private String An_ID;
}
