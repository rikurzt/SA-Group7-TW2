package com.example.sa_g7_tw2_spring.Domain.Observer;

import java.util.concurrent.ExecutionException;

public interface ObservableSubject {
        void notifyObservers() throws ExecutionException, InterruptedException;

        void attach(Observer observer);

        void detach(Observer observer);
}
