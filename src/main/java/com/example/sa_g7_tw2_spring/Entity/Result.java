package com.example.sa_g7_tw2_spring.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Result {
    private LocalDateTime time;
    boolean result;
    private double length;

    public Result(boolean analyzeResult) {
        result=analyzeResult;
    }

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
