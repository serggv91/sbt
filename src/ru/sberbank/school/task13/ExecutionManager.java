package ru.sberbank.school.task13;
public interface ExecutionManager {
    Context execute(Runnable callback, Runnable... tasks);
}
