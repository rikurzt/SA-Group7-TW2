package com.example.sa_g7_tw2_spring.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SendFindRequestResultVO extends ValueObject{
    private String date;
    private boolean result;
    private float percentage;
}
