package com.example.sa_g7_tw2_spring.ValueObject;

import com.google.firestore.v1.TransactionOptions;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FindRequestVO {
    private final String account;
    private final String token;
    private final String message;
}
