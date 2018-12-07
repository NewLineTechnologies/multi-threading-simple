package net.nlt.test.configuration;

import net.nlt.test.service.CounterService;
import net.nlt.test.domain.thread.CounterThreadPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class CommonConfig {

    @Bean
    @Scope("prototype")
    public CounterThreadPool threadPool(CounterService counterService) {
        return new CounterThreadPool(Runtime.getRuntime().availableProcessors() + 1, counterService);
    }
}
