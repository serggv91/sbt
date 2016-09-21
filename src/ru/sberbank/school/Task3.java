/**
 * Created by Sergei on 17.09.2016.
 */
package ru.sberbank.school;
import java.io.*;
import java.util.*;


/**
 * Created by Sergei on 12.08.2016.
 */
public class Task3
{

    private class LengthFirstComparator implements Comparator<String> {
        @Override
        public int compare(String first, String second) {
            if (first.length()!= second.length()) {
                return first.length()-second.length();
            }
            return first.compareTo(second);
        }
    }

    private class ReverseIterator implements Iterator<String> {

        private final List<String> list;
        private int index;

        public ReverseIterator(List<String> list) {
            this.list = list;
            index = list.size();
        }

        @Override
        public String next() {
            if(hasNext()) {
                return list.get(--index);
            } else {
                throw new NoSuchElementException("There are no elements size = " + list.size());
            }
        }

        @Override
        public boolean hasNext() {
            return (index > 0);
        }

        @Override
        public void remove() {
            if(index < 0 || index == list.size()) {
                throw new IllegalStateException("can not delete element");
            }
            list.remove(--index);
        }
    }

    public boolean run1(File sourceFile) {
        /**
         *  1. Number of different words in the file
         */

        Set<String occurrences = new HashSet<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(sourceFile));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] words = line.split("\\W+");
                for ( String word : words)
                    occurrences.add(word);
            }
            bufferedReader.close();
        } catch (Exception e) {
            return false;
        }

        System.out.println("1. Number of different words in the file: " + occurrences.size());
    }

    public boolean run2(File sourceFile) {
        /**
         *  2. List of the sorted words + 3. Number of words occurences
         */

        /**
         *  Read lines to the Map
         */
        Map<String, Integer> occurrences = new TreeMap<>(new LengthFirstComparator()); // Keys in TreeMap are sorted
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(sourceFile));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] words = line.split("\\W+");
                for ( String word : words) {
                    Integer oldCount = occurrences.get(word);
                    if (oldCount == null)
                        oldCount = 0;
                    occurrences.put(word, oldCount + 1);
                }

            }
            bufferedReader.close();
        } catch (Exception e) {
            return false;
        }

        System.out.println("2. List of the sorted words + 3. Number of words occurences");
        for (Map.Entry<String, Integer> entry : occurrences.entrySet())
            System.out.println(entry.getKey() + " [" + entry.getValue() + "]");
    }

    public boolean run4(File sourceFile) {
        /**
         * 4. All lines in reverse order
         */

        ArrayDeque<String> lines= new ArrayDeque<String>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(sourceFile));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            bufferedReader.close();
        } catch (Exception e) {
            return false;
        }

        System.out.println("4. All lines in reverse order");
        ListIterator li = lines.descendingIterator();
        while(li.hasNext())
            System.out.println(li.next());
    }



    public boolean run (File sourceFile, int[] order) {
        if (sourceFile == null)
            return false;

        /**
         *  Read lines to the Map
         */
        Map<String, Integer> occurrences = new TreeMap<>(new LengthFirstComparator()); // Keys in TreeMap are sorted
        ArrayList<String> lines = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(sourceFile));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
                String[] words = line.split("\\W+");
                for ( String word : words) {
                    Integer oldCount = occurrences.get(word);
                    if (oldCount == null)
                        oldCount = 0;
                    occurrences.put(word, oldCount + 1);
                }

            }
            bufferedReader.close();
        } catch (Exception e) {
            return false;
        }

        /**
         *  1. Number of different words in the file
         */
        System.out.println("1. Number of different words in the file: " + occurrences.size());

        /**
         *  2. List of the sorted words + 3. Number of words occurences
         */
        System.out.println("2. List of the sorted words + 3. Number of words occurences");
        for (Map.Entry<String, Integer> entry : occurrences.entrySet())
            System.out.println(entry.getKey() + " [" + entry.getValue() + "]");

        /**
         * 4. All lines in reverse order
         */
        System.out.println("4. All lines in reverse order");
        ListIterator li = lines.listIterator(lines.size());
        while(li.hasPrevious())
            System.out.println(li.previous());

        /**
         * 5. All lines in reverse order, using own iterator
         */
        System.out.println("5. All lines in reverse order using own iterator");
        ReverseIterator ri = new ReverseIterator(lines);
        while(ri.hasNext())
            System.out.println(ri.next());

        /**
         * 6. Specified order of lines
         */
        System.out.println("5. Specified order of lines");
        for (int i = 0; i < order.length; i++)
            try {
                System.out.println(lines.get(order[i]));
            }  catch (IndexOutOfBoundsException e) {
                System.out.println("Wrong index");
                return false;
            }
        return true;
    }

    public static void main(String[] args) {
        Task3 task = new Task3();
        int[] order = {5,2,1};
        boolean res = task.run(new File("test.txt"), order);
    }

}
