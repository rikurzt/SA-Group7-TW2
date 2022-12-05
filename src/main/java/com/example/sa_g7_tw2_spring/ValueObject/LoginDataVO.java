package com.example.sa_g7_tw2_spring.ValueObject;


import lombok.AllArgsConstructor;
import lombok.Data;




@Data
@AllArgsConstructor
public class LoginDataVO implements ValueObject{
    private final String account;
    private final String password;

}
