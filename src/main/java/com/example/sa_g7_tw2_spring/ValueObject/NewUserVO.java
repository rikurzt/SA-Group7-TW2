package com.example.sa_g7_tw2_spring.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewUserVO extends ValueObject{
    private String account;
    private String userName;
    private String password;
    private String gender;
    private int age;
    private String phone;
    private String address;
    private String familyID;
    private String familyName;
    private String familyPhone;
    private String token;
    private String wristbandName;

}
