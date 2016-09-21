/**
 * Created by Sergei on 20.09.2016.
 */
package ru.sberbank.school;
import java.util.*;

public class CountMap<T> {
    private final Map<T,Integer> counts;

    public CountMap() {
        counts = new HashMap<T, Integer>();
    }

    // добавляет элемент в этот контейнер.
    void add(T element) {
        Integer oldCount = counts.get(element);
        if (oldCount == null)
            oldCount = 0;
        counts.put(element, oldCount + 1);
    }

    //Возвращает количество добавлений данного элемента
    int getCount(T element){
        Integer count = counts.get(element);
        if (count == null)
            count = 0;
        return count;
    }

    //Удаляет элемент и контейнера и возвращает количество его добавлений(до удаления)
    int remove(T element){
        Integer count = counts.get(element);
        if (count == null)
            return 0;
        else
            counts.remove(element);
        return count;
    }

    //количество разных элементов
    int size(){
        return counts.size();
    }

    //Добавить все элементы из source в текущий контейнер, при совпадении ключей,     суммировать значения
    void addAll(CountMap<? extends T> source){
        Map s = source.toMap();
        Set<Map.Entry<T, Integer>> entries = s.entrySet();
        for (Map.Entry<T, Integer> entry : entries) {
            T key = entry.getKey();
            Integer value = entry.getValue();
            Integer count = this.counts.get(key);
            if (count == null)
                this.counts.put(key, 1);
            else
                this.counts.put(key, count + value);
        }


    }

    //Вернуть java.util.Map. ключ - добавленный элемент, значение - количество его добавлений
    Map<T, Integer> toMap(){
        return counts;
    }

    //Тот же самый контракт как и toMap(), только всю информацию записать в destination
    void toMap(Map<? super T, Integer> destination){
        destination.putAll(counts);
    }

}

