package ru.sberbank.school.task12;

/**
 * Created by Sergei on 22.10.2016.
 */
import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueue<E>{

    private Queue<E> queue = new LinkedList<E>();


    public synchronized void enqueue(E e) {
        queue.add(e);
        // Wake up anyone waiting on the queue to put some item.
        notifyAll();
    }


    public synchronized E dequeue(){
        E e = null;

        while(queue.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e1) {
                return e;
            }
        }
        e = queue.remove();
        return e;
    }

    public synchronized int size() {
        return queue.size();
    }

}
