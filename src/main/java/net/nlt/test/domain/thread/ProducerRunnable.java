package net.nlt.test.domain.thread;

import lombok.RequiredArgsConstructor;
import net.nlt.test.domain.Counter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@RequiredArgsConstructor
public class ProducerRunnable implements Runnable {

    private final Counter counter;

    @Override
    public void run() {
        if (counter.canModify()) {
            counter.increment();
        }
    }
}
