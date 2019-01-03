package com.jeff4w.example.restapi;

import org.crazycake.shiro.RedisManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@SpringBootApplication
public class RestapiApplication {

    @Bean
    public JedisPool redisPoolFactory() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(8);
        jedisPoolConfig.setMaxWaitMillis(-1);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, "192.168.1.122", 6379, 1800);
        return jedisPool;
    }

    @Bean
    public RedisManager redisManager(JedisPool jedisPool){
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("192.168.1.122:6379");
        redisManager.setJedisPool(jedisPool);
        return redisManager;
    }

    public static void main(String[] args) {
        SpringApplication.run(RestapiApplication.class, args);
    }

}

