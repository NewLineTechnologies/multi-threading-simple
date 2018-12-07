package net.nlt.test.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.nlt.test.domain.Counter;
import net.nlt.test.domain.thread.ConsumerRunnable;
import net.nlt.test.domain.thread.ProducerRunnable;
import net.nlt.test.service.CounterService;
import net.nlt.test.domain.thread.CounterThreadPool;
import net.nlt.test.service.ThreadService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class ThreadServiceImpl implements ThreadService {

    private final ApplicationContext context;
    private final CounterService counterService;

    @Override
    @SneakyThrows
    public void createThreads(Integer producerThreads, Integer consumerThreads) {
        CounterThreadPool threadPool = context.getBean(CounterThreadPool.class);
        Collection<Runnable> runnableCollection = prepareRunnableCollection(producerThreads, consumerThreads);

        execute(runnableCollection, threadPool);

        counterService.saveIfReaching();
    }

    private Collection<Runnable> prepareRunnableCollection(Integer producerThreads, Integer consumerThreads) {
        Collection<Runnable> runnableCollection = new HashSet<>();
        for (int i = 0; i < producerThreads; i++) {
            runnableCollection.add((context.getBean(ProducerRunnable.class)));
        }
        for (int i = 0; i < consumerThreads; i++) {
            runnableCollection.add((context.getBean(ConsumerRunnable.class)));
        }

        return runnableCollection;
    }

    @SneakyThrows
    private void execute(Collection<Runnable> runnable, CounterThreadPool threadPool) {
        runnable.forEach(threadPool::execute);
        joinThreads(threadPool);
    }

    private void joinThreads(CounterThreadPool threadPool) throws InterruptedException {
        for (Thread thread : threadPool.getThreads()) {
            thread.join();
        }
    }
}
