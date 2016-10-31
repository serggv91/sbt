package ru.sberbank.school.task13;

import java.util.concurrent.Callable;

/**
 * Created by Sergei on 29.10.2016.
 */
public class Task<T> {

    private final Callable<? extends T> callable;

    private volatile T calculatedValue;
    private volatile TaskRuntimeException exception;

    public Task(Callable<? extends T> callable) {
        this.callable = callable;
    }

    public T get() throws TaskRuntimeException{
        if (exception != null) {
            throw exception;
        }

        if (calculatedValue == null)
            run();

        return calculatedValue;
    }

    public synchronized void run() throws TaskRuntimeException {
        try {
            calculatedValue = callable.call();
        } catch (Exception e) {
            exception = new TaskRuntimeException("callable exception", e);
            throw exception;
        }
    }
}
