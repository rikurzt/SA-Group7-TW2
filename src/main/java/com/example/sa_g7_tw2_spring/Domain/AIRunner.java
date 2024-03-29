package com.example.sa_g7_tw2_spring.Domain;

import com.example.sa_g7_tw2_spring.utils.ScriptRunner;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AIRunner {

    public boolean Analyze(double[] data){
        File processPythonFile = new File("src/py/ai.py");
        boolean result = ScriptRunner.runScript((BufferedReader br) -> {
            List<String> lines = br.lines().toList();
            for (String line:lines
                 ) {
                System.out.println(line);
            }
            try {
                return Boolean.valueOf(lines.get(lines.size() - 1));
            } catch (Exception e) { }
            return true;
        },"python",processPythonFile.getAbsolutePath(), String.join(" ", Arrays.stream(data).mapToObj(d->String.valueOf(d)).collect(Collectors.toList())));
        return result;
    }
}
