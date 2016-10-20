package ru.sberbank.school.task11;
public class Task11 implements Summator{
    public double sum(double a, double b) {
        return a+b;
    }


    public static void main(String[] args) {
        Summator example = new Task11();
        Summator myProxy = (Summator) CacheProxyImpl.newInstance(example, CacheProxyImpl.MemoryType.MEMORY, "");
        myProxy.sum(4, 2);
        myProxy.sum(4, 2);
    }
}

