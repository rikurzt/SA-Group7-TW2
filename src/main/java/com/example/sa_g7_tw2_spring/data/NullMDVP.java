package com.example.sa_g7_tw2_spring.data;

import com.example.sa_g7_tw2_spring.Domain.AIRunner;

public class NullMDVP implements MDVP{
    @Override
    public double[] getMdvp() {
        return new double[16];
    }

    @Override
    public boolean analyze(AIRunner runner) {
        return false;
    }
}
