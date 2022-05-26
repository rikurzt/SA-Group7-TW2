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

    public boolean Analyze(double[] data){
        boolean result = ScriptRunner.runScript((BufferedReader br) -> {
            List<String> lines = br.lines().toList();
            try {
                return Boolean.valueOf(lines.get(lines.size() - 1));
            } catch (Exception e) { }
            return true;
        },"py","test/ai.py", String.join(" ", Arrays.stream(data).mapToObj(d->String.valueOf(d)).collect(Collectors.toList())));
        return result;
    }
}
