package com.huanyu.weekly.redis.config;

import com.huanyu.weekly.redis.listener.TestMessageListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisConfig {

    @Bean
    public RedisMessageListenerContainer messageListenerContainer(RedisConnectionFactory redisConnectionFactory,
                                                                  MessageListener messageListener) {
        RedisMessageListenerContainer messageListenerContainer = new RedisMessageListenerContainer();
        messageListenerContainer.setConnectionFactory(redisConnectionFactory);
        messageListenerContainer.addMessageListener(messageListener, new ChannelTopic("test"));
        return messageListenerContainer;
    }

    @Bean
    public MessageListener messageListener(TestMessageListener testMessageListener) {
        return new MessageListenerAdapter(testMessageListener);
    }
}
