package com.example.sa_g7_tw2_spring.Domain.Observer;

import java.util.concurrent.ExecutionException;

public interface Observer {
    void update(ObservableSubject subject) throws ExecutionException, InterruptedException;
}
