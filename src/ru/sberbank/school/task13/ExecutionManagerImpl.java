package ru.sberbank.school.task13;


public class ExecutionManagerImpl implements ExecutionManager {

    private class ContextImpl implements Context {
        private List<Thread> threads = new ArrayList<>();;
        private volatile int failedNumber = 0;
        private volatile int completedNumber = 0;
        private volatile int interruptedNumber = 0;
        private final Object object = new Object();

        public ContextImpl(Runnable callback, Runnable... tasks) {
            Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
                synchronized (object) {
                    failedNumber++;
                }
            });

            for (Runnable task : tasks) {
                Thread t = new Thread(() -> {
                    if (Thread.interrupted()) {
                        synchronized (object) {
                            interruptedNumber++;
                        }
                        return;
                    }
                    task.run();
                    synchronized (object) {
                        completedNumber++;
                    }
                });
                threads.add(t);
                t.start();
            }

            while(true)
                if (this.isFinished())
                    break;


            new Thread(() -> {
                callback.run();
            }).start();
        }


        @Override
        public int getCompletedTaskCount() {
            return completedNumber;
        }

        @Override
        public int getFailedTaskCount() {
            return failedNumber;
        }

        @Override
        public int getInterruptedTaskCount() {
            return interruptedNumber;
        }

        @Override
        public void interrupt() {
            threads
                    .stream()
                    .forEach(t -> t.interrupt());
        }

        @Override
        public boolean isFinished() {
            return threads.size() - interruptedNumber - completedNumber - failedNumber == 0;
        }
    }

    @Override
    public Context execute(Runnable callback, Runnable... tasks) {
        return new ContextImpl(callback, tasks);
    }

}
