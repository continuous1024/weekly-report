package com.huanyu.weekly.dubbo.provider.service;

import com.huanyu.weekly.dubbo.api.AsyncDemoService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.CompletableFuture;

@Service(version = "${demo.service.version}")
public class AsyncDemoServiceImpl implements AsyncDemoService {

    @Value("${dubbo.application.name}")
    private String serviceName;

    @Override
    public CompletableFuture<String> hello(String name) {
        return CompletableFuture.supplyAsync(() -> String.format("[%s] : Hello, %s", serviceName, name));
    }
}
