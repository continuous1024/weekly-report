package com.huanyu.weekly.dubbo.api;

import java.util.concurrent.CompletableFuture;

public interface AsyncDemoService {
    CompletableFuture<String> hello(String name);
}
