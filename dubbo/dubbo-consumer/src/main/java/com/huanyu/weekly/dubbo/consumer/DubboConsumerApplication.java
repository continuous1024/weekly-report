package com.huanyu.weekly.dubbo.consumer;

import com.huanyu.weekly.dubbo.api.AsyncDemoService;
import com.huanyu.weekly.dubbo.api.DemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;

@EnableAutoConfiguration
public class DubboConsumerApplication {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Reference(version = "${demo.service.version}")
    private DemoService demoService;

    @Reference(version = "${demo.service.version}")
    private AsyncDemoService asyncDemoService;

    public static void main(String[] args) {
        SpringApplication.run(DubboConsumerApplication.class).close();
    }

    @Bean
    public ApplicationRunner runner() {
        return args -> {
            logger.info(demoService.hello("world"));

            logger.info(asyncDemoService.hello("world").get());
        };
    }
}
