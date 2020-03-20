package com.huanyu.weekly.redis.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class IndexController {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @RequestMapping(value = "test")
    public void testPubSub(@RequestParam Integer len) {
        for (int i = 0; i < len; i++) {
            redisTemplate.convertAndSend("test", "hello");
        }
    }

}
