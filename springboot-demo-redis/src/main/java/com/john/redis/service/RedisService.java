package com.john.redis.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: lishijia
 * @create: 2019-12-28 18:42
 **/
@Slf4j
@Service
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    public void testCacheString(){
        log.info("[redis test] test connect");
        redisTemplate.boundValueOps("test").set("bb");
    }

    public void testGetFromCache(){
        log.info("[redis test] test connect test value = {}", redisTemplate.boundValueOps("test").get());
    }

}