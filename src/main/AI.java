package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class AI {
    LocalDateTime time;

    void retrieveProcessedData(LocalDateTime time,DataProcessing dp) {
        this.time = time;
        analyze(dp.returnProcessedData());

    }

    void analyze(List<Double> data){
        boolean result = ScriptRunner.runScript((InputStream stream) -> {
            var br = new BufferedReader(new InputStreamReader(stream));
            try {
                return Boolean.valueOf(br.readLine());
            } catch (IOException e) { }
            return true;
        },"ai.py", String.join(" ", data.stream().map(d->String.valueOf(d)).collect(Collectors.toList())));
        saveResultToDataBase(result);
    }

    void saveResultToDataBase(boolean result){

    }
}
