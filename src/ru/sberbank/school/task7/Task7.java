package ru.sberbank.school.task7;
public class Task7 implements Summator{
    public double sum(double a, double b) {
        return a+b;
    }


    public static void main(String[] args) {
        Summator example = new Task7();
        Summator myProxy = (Summator) CacheProxyImpl.newInstance(example);
        myProxy.sum(4, 2);
        myProxy.sum(4, 2);
    }
}

