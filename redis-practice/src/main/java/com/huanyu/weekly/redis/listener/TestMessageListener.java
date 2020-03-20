package com.huanyu.weekly.redis.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class TestMessageListener {
    public void handleMessage(String message) throws InterruptedException {
        log.info("Recevice Message Content: {}", message);
        TimeUnit.SECONDS.sleep(10);
    }
}
