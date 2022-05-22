package com.example.sa_g7_tw2_spring.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Result {
    LocalDateTime time;
    boolean result;
    double length;
}
