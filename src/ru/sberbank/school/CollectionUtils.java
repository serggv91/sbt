/**
 * Created by Sergei on 20.09.2016.
 */
package ru.sberbank.school;
import java.util.*;
public class CollectionUtils {
    public static<T> void addAll(List<? extends T> source, List<? super T> destination) {
        destination.addAll(source);
    }

    public static<T> List<T> newArrayList() {
        return new ArrayList<>();
    }

    public static<T> int indexOf(List<? extends T> source, T o) {
        return source.indexOf(o);
    }

    public static<T> List<T> limit(List<? extends T> source, int size) {
        List <T> destination = new ArrayList<>();
        size = Math.min(size, source.size());
        for (int i = 0; i < size; i++)
            destination.add(source.get(i));
        return destination;
    }

    public static<T> void add(List<? super T> destination, T o) {
        destination.add(o);
    }

    public static<T> void removeAll(List<? extends T> removeFrom, List<? super T> c2) {
        c2.addAll(removeFrom);
        removeFrom.clear();
    }

    public static<T> boolean containsAll(List<? super T> c1, List<? extends T> c2) {
        return c1.containsAll(c2);
    }

    public static<T> boolean containsAny(List<? extends T> c1, List<? super T> c2) {
        HashSet<T> hs = new HashSet<T>(c1);
        hs.retainAll(c2);
        return !hs.isEmpty();
    }

    public static<T> List<T> range(List<? extends Comparable<? super T>> list, T min, T max) {
        List<T> destination = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if ((list.get(i)).compareTo(min) >= 0 && (list.get(i)).compareTo(max) <= 0)
                destination.add((T)list.get(i));
        }
        return destination;
    }

    public static<T> List<T> range(List<? extends T> list, T min, T max, Comparator<? super T> comparator) {
        List<T> destination = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if ( comparator.compare(list.get(i), min) >= 0 && comparator.compare(max, list.get(i)) >=0 )
                destination.add(list.get(i));
        }
        return destination;
    }

    public static void main(String[] args) {
        List<Integer> a= Arrays.asList(8,1,3,5,6, 4);
        List<Integer> b = new ArrayList<>();
        addAll(a, b);
        System.out.println(b);

    }

}
