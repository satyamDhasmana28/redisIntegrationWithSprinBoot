package com.satyam.RedisSpringIntegration.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.satyam.RedisSpringIntegration.Model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class UserConfig {
//    this bean will helps to get the redis connection
    @Bean
    public LettuceConnectionFactory getConnection(){
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

//    this bean will hepls to create redisTemplate
    @Bean
    public RedisTemplate getRedisTemplate(){
        RedisTemplate<String,User> redisTemplate = new RedisTemplate<String, User>();
        RedisSerializer<String> stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        redisTemplate.setValueSerializer(jdkSerializationRedisSerializer);
        redisTemplate.setHashValueSerializer(jdkSerializationRedisSerializer);
        redisTemplate.setConnectionFactory(getConnection());
        return redisTemplate;
    }

    @Bean
    public ObjectMapper getObjectMapper(){
        return new ObjectMapper();
    }
}
