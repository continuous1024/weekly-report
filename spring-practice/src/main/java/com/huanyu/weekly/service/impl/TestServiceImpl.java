package com.huanyu.weekly.service.impl;

import com.huanyu.weekly.service.TestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {
    @Override
    public String test() {
        return "test";
    }
}
