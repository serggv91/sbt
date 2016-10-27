package ru.sberbank.school.task12;


/**
 * Created by Sergei on 22.10.2016.
 */
public class FixedThreadPool implements ThreadPool {
    private final int CAPACITY;
    private BlockingQueue<Runnable> taskQueue = new BlockingQueue<>();

    public FixedThreadPool(int capacity){
        this.CAPACITY = capacity;
        start();
    }
    @Override
    public void start() {
        for(Integer i = 0; i < CAPACITY; i++){
            Thread thread = new Thread(new Worker(taskQueue, i.toString()));
            thread.start();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        taskQueue.enqueue(runnable);
    }

    private class Worker implements Runnable{

        private BlockingQueue<Runnable> taskQueue;
        private String name;

        public Worker(BlockingQueue<Runnable> myQueue, String name){
            this.taskQueue = myQueue;
            this.name = name;
        }

        @Override
        public void run() {
            while(true){
                Runnable r = taskQueue.dequeue();
                // print the dequeued item
                System.out.println(" Taken Item by thread name:" + this.name );
                // run it
                r.run();
                System.out.println(" Task completed of thread:" + this.name);
            }
        }
    }
}
