package net.nlt.test.domain;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Component
public class Counter {

    private AtomicInteger value = new AtomicInteger(50);

    private LocalDateTime lastReachedAt;

    public void increment() {
        int counterValue = value.incrementAndGet();
        printCurrentValue(counterValue, "increment");
        checkAndSafeIfReached();
    }

    public void decrement() {
        int counterValue = this.value.decrementAndGet();
        printCurrentValue(counterValue, "decrement");
        checkAndSafeIfReached();
    }

    public void setValue(int newValue) {
        value.set(newValue);
    }

    public synchronized boolean canModify() {
        return value.intValue() != 0 && value.intValue() != 100;
    }

    private void printCurrentValue(int counterValue, String operation) {
        System.out.printf("Counter value: %d. Counter was %s by: %s%n", counterValue, operation, Thread.currentThread().getName());
    }

    private void checkAndSafeIfReached() {
        if (!canModify()) {
            lastReachedAt = LocalDateTime.now();
        }
    }
}
