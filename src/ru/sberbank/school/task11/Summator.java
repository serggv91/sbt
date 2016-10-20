package ru.sberbank.school.task11;
public interface Summator {
    @Cache
    double sum(double a, double b);
}
