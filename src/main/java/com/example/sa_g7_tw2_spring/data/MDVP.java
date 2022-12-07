package com.example.sa_g7_tw2_spring.data;

import com.example.sa_g7_tw2_spring.Domain.AIRunner;

public interface MDVP {
    double[] getMdvp();

    boolean analyze(AIRunner runner);
}
