package com.john.redis.service;

import com.john.redis.handler.RedisHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

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

    @Autowired
    private RedisHandle redisHandle;

    public void testCacheString(){
        log.info("[redis test] test connect");
        redisTemplate.boundValueOps("test").set("bb");
    }

    public void testGetFromCache(){
        log.info("[redis test] test connect test value = {}", redisTemplate.boundValueOps("test").get());
    }

    public void pushDataToSet(){
        Set<String> set = new HashSet<>();
        for (int i=0;i<100;i++){
            set.add(i + "");
        }
        log.info("[redis test] key={} push set data to redis size = {}", "setKey", set.size());
        redisHandle.addSet("setKey", set.toArray());
    }

    public void hasMember(String member){
        boolean result = redisHandle.hasSetValue("setKey", member);
        log.info("[redis test] key={} has member = {} result = {}", "setKey", member, result);
    }



}