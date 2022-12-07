package com.example.sa_g7_tw2_spring.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class UploadVO implements ValueObject{

    private final MultipartFile multipartFile;
    private final String id;
    private final String token;

}
