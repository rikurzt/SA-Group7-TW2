package com.example.sa_g7_tw2_spring.DataProcessing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AI {

    boolean Analyze(double[] data){
        boolean result = ScriptRunner.runScript((InputStream stream) -> {
            var br = new BufferedReader(new InputStreamReader(stream));
            try {
                return Boolean.valueOf(br.readLine());
            } catch (IOException e) { }
            return true;
        },"ai.py", String.join(" ", Arrays.stream(data).mapToObj(d->String.valueOf(d)).collect(Collectors.toList())));
        return result;
    }
}
