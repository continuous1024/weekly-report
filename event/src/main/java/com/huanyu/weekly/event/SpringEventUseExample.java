package com.huanyu.weekly.event;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@SpringBootApplication
@EnableAsync
public class SpringEventUseExample {

    @Resource
    private ApplicationEventPublisher eventPublisher;

    @Resource
    private EventBus eventBus;

    @Resource
    private AsyncEventBus asyncEventBus;

    @Component
    public class SpringRunner implements ApplicationRunner {

        public void run(ApplicationArguments args) throws Exception {
            eventPublisher.publishEvent(SimpleEvent.builder().message("Hello, Spring Event.").build());
            eventBus.post(SimpleEvent.builder().message("Hello, Guava Event.").build());
            asyncEventBus.post(SimpleEvent.builder().message("Hello, Guava Async Event.").build());
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringEventUseExample.class, args);
    }
}
