package com.example.sa_g7_tw2_spring.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


@Data
@AllArgsConstructor
public class WristbandVO extends ValueObject{
    private final String wristbandID;
    private final MultipartFile file;
}
