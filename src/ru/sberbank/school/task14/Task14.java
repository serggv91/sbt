package ru.sberbank.school.task14;
public class Task14 implements Summator{
    public double sum(double a, double b) {
        return a+b;
    }


    public static void main(String[] args) {
        Summator example = new Task14();
        Summator myProxy = (Summator) CacheProxyImpl.newInstance(example);
        myProxy.sum(4, 2);
        myProxy.sum(4, 2);
    }
}

