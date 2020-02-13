package com.huanyu.weekly.event.guava;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.huanyu.weekly.event.SimpleEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author arron
 */
@Component
@Slf4j
public class GuavaEventListener {

    @Resource
    private EventBus eventBus;

    @PostConstruct
    public void register() {
        eventBus.register(this);
    }

    @Subscribe
    public void handleSync(SimpleEvent simpleEvent) {
        log.info("Guava 同步事件处理， 接收到事件为：" + simpleEvent);
    }
}
