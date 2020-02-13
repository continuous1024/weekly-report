package com.huanyu.weekly.event.guava;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

@Configuration
public class GuavaEventConfig {
    @Bean("eventBus")
    public EventBus getEventBus() {
        return new EventBus();
    }

    @Bean("asyncEventBus")
    public AsyncEventBus getAsyncEventBus() {
        return new AsyncEventBus(Executors.newFixedThreadPool(8));
    }
}
