package com.example.sa_g7_tw2_spring.data.NullObject;

import com.example.sa_g7_tw2_spring.Domain.AIRunner;

public class RealMDVP implements MDVP{
    final double mdvp[];

    public RealMDVP(double data[]) {
        mdvp = data;
    }

    public double[] getMdvp() {
        return mdvp;
    }

    @Override
    public boolean analyze(AIRunner runner) {
        return runner.Analyze(getMdvp());
    }
}
