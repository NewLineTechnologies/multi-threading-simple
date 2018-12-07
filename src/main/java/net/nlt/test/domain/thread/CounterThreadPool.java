package net.nlt.test.domain.thread;

import lombok.SneakyThrows;
import net.nlt.test.service.CounterService;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;

public class CounterThreadPool implements Executor {

    private final CounterService counterService;
    private final Queue<Runnable> workQueue = new ConcurrentLinkedQueue<>();
    private volatile boolean isRunning = true;
    private List<Thread> threads = new ArrayList<>();

    @SneakyThrows
    public CounterThreadPool(int nThreads, CounterService counterService) {
        this.counterService = counterService;
        for (int i = 0; i < nThreads; i++) {
            createThread();
        }
    }

    public List<Thread> getThreads() {
        return threads;
    }

    @Override
    public void execute(Runnable command) {
        if (isRunning) {
            workQueue.offer(command);
        }
    }

    private void createThread() {
        Thread thread = new Thread(new TaskWorker());
        threads.add(thread);
        thread.start();
    }

    private boolean canExecuteNextTask() {
        return isRunning = counterService.canModifyCounter() && isRunning;
    }


    private final class TaskWorker implements Runnable {

        @Override
        @SneakyThrows
        public void run() {
            while (isRunning) {
                Runnable nextTask;
                if ((nextTask = workQueue.poll()) != null && canExecuteNextTask()) {
                    nextTask.run();
                    isRunning = workQueue.peek() != null;
                }
            }
        }
    }
}