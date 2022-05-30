package com.example.sa_g7_tw2_spring.ValueObject;


import lombok.AllArgsConstructor;
import lombok.Data;




@Data
@AllArgsConstructor
public class LoginDataVo {
    private String account;
    private String password;
}
