package com.example.sa_g7_tw2_spring.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserVO extends ValueObject{

    private String userName;
    private String account;
    private String gender;
    private int age;
    private String phone;
    private String address;
    private String wristbandID;



}
