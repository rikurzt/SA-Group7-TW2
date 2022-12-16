package com.example.sa_g7_tw2_spring.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmergencyContectVO extends ValueObject{
    private String familyID;
    private String familyName;
    private String familyPhone;
}
