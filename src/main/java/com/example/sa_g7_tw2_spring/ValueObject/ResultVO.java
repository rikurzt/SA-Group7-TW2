package com.example.sa_g7_tw2_spring.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ResultVO {
    private final LocalDateTime time;
    boolean final result;
    private final double length;
    private final double ID;


    public boolean getResult() {
        return result;
    }

    public LocalDateTime getTime() {
        return time;
    }
    public double getLength() {
        return length;
    }
}
