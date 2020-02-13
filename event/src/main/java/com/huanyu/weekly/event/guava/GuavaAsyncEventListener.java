package com.huanyu.weekly.event.guava;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import com.huanyu.weekly.event.SimpleEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
@Slf4j
public class GuavaAsyncEventListener {

    @Resource
    public AsyncEventBus asyncEventBus;

    @PostConstruct
    public void register() {
        asyncEventBus.register(this);
    }

    @Subscribe
    public void handleAsync(SimpleEvent simpleEvent) {
        log.info("Guava 异步事件处理， 接收到事件为：" + simpleEvent);
    }
}
