package task11;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Sergei on 22.10.2016.
 */
public class ScalableThreadPool implements ThreadPool {
    private final int MIN;
    private final int MAX;
    private BlockingQueue<Runnable> taskQueue = new BlockingQueue<>();
    private List<Thread> threads = new LinkedList<>();

    public ScalableThreadPool(int min, int max){
        this.MIN = min;
        this.MAX = max;
    }
    @Override
    public void start() {
        for(Integer i = 0; i < this.MIN; i++){
            Thread thread = new Thread(new Worker(taskQueue, i.toString()));
            thread.start();
        }
        while (true) {
            if ((taskQueue.size()) > MIN
                    && threads.size() < MAX) {
                for (Integer i = MIN + 1; i <= MAX; i++) {
                    Thread t = new Thread(new Worker(taskQueue, i.toString()));
                    t.start();
                    threads.add(t);
                    System.out.println("THREADS_SIZE++ = " + threads.size());
                }
            } else if ((taskQueue.size()) < MIN
                    && threads.size() > MIN) {
                for (int i = MIN+ 1; i <= threads.size(); i++) {
                    Thread t = threads.get(i-1);
                    t.interrupt();
                    threads.remove(t);
                    System.out.println("THREADS_SIZE-- = " + threads.size());
                }
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

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
