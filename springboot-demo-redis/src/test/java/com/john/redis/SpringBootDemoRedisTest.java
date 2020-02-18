package com.john.redis;

import com.john.redis.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @description:
 * @author: lishijia
 * @create: 2019-12-28 15:47
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootDemoRedisTest {

    @Autowired
    private RedisService redisService;

    @Test
    public void testConfigurer() {
        System.out.println("make it test");
        redisService.testCacheString();
    }

    @Test
    public void testGetFromCache() {
        redisService.testGetFromCache();
    }

    @Test
    public void testAddSetValue(){
        redisService.pushDataToSet();
    }

    @Test
    public void testSetHasMember(){
        redisService.hasMember("1");
    }



}