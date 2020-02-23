package com.huanyu.weekly.dubbo.provider.service;

import com.huanyu.weekly.dubbo.api.DemoService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;

@Service(version = "${demo.service.version}")
public class DemoServiceImpl implements DemoService {
    /**
     * The default value of ${dubbo.application.name} is ${spring.application.name}
     */
    @Value("${dubbo.application.name}")
    private String serviceName;

    @Override
    public String hello(String name) {
        return String.format("[%s] : Hello, %s", serviceName, name);
    }
}
