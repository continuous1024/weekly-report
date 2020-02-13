package com.huanyu.weekly.event.spring;

import com.huanyu.weekly.event.SimpleEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author arron
 */
@Component
@Slf4j
public class SpringEventListener {
    @EventListener
    public void handleSync(SimpleEvent simpleEvent) {
        log.info("Spring 同步事件处理， 接收到事件为：" + simpleEvent);
    }

    @Async
    @EventListener
    public void handleAsync(SimpleEvent simpleEvent) {
        log.info("Spring 异步事件处理， 接收到事件为：" + simpleEvent);
    }
}
