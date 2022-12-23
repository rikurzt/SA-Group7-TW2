package com.example.sa_g7_tw2_spring.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewUserVO extends ValueObject{
    private final String account;
    private final String userName;
    private final String password;
    private final String gender;
    private final int age;
    private final String phone;
    private final String address;
    private final String familyID;
    private final String familyName;
    private final String familyPhone;
    private final String token;
    private final String wristbandName;

}
