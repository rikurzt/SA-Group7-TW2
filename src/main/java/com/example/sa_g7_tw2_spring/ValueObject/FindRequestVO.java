package com.example.sa_g7_tw2_spring.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class FindRequestVO {
    private String account;
    private String token;
    private String message;

}
