package com.john.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
// 开启缓存，需要显示的指定
@EnableCaching
// 开启异步
@EnableAsync
public class SpringBootDemoRedisApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoRedisApplication.class, args);
	}
}
