package ru.sberbank.school.task6;

public interface Factorable {

    <T> T getBean(Class<T> cls);

    void close();

    void registryShutdownHook();
}
