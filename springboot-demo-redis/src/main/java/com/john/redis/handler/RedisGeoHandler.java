package com.john.redis.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RedisGeoHandler {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 添加经纬度信息,时间复杂度为O(log(N))
     * redis 命令：geoadd cityGeo 116.405285 39.904989 "北京"
     *
     * @param k
     * @param point
     * @param m
     */
    public Long addGeoPoin(Object k, Point point, Object m) {
        Long addedNum = redisTemplate.opsForGeo().add(k, point, m);
        return addedNum;
    }

    /**
     * 添加经纬度信息,时间复杂度为O(log(N))
     * redis 命令：geoadd cityGeo 116.405285 39.904989 "北京"
     *
     * @param k
     * @param point
     * @param value
     */
    public Long addGeoPointProtoStuff(Object k, Point point, byte[] value) {
        byte[] key = redisTemplate.getKeySerializer().serialize(k);
        return (Long) redisTemplate.execute((connection) -> {
            return connection.geoAdd(key, point, value);
        }, true);
    }

    /**
     * 查找指定key的经纬度信息，可以指定多个key，批量返回
     * redis命令：geopos cityGeo 北京
     *
     * @param k
     * @param m
     */
    public List<Point> geoGet(Object k, Object... m) {
        List<Point> points = redisTemplate.opsForGeo().position(k, m);
        return points;
    }

    /**
     * 返回两个地方的距离，可以指定单位，比如米m，千米km，英里mi，英尺ft
     * redis命令：geodist cityGeo 北京 上海
     *
     * @param k
     * @param mk1
     * @param mk2
     * @param metric
     * @return
     */
    public Distance geoDist(Object k, Object mk1, Object mk2, Metric metric) {
        Distance distance = redisTemplate.opsForGeo().distance(k, mk1, mk2, metric);
        return distance;
    }

    /**
     * 根据给定的经纬度，返回半径不超过指定距离的元素,时间复杂度为O(N+log(M))，N为指定半径范围内的元素个数，M为要返回的个数
     * redis命令：georadius cityGeo 116.405285 39.904989 100 km WITHDIST WITHCOORD ASC COUNT 5
     *
     * @param k
     * @param circle
     * @param args
     */
    public GeoResults<RedisGeoCommands.GeoLocation<Object>> nearByXY(Object k, Circle circle, RedisGeoCommands.GeoRadiusCommandArgs args) {
        GeoResults<RedisGeoCommands.GeoLocation<Object>> results = redisTemplate.opsForGeo().radius(k, circle, args);
        return results;
    }

    public GeoResults<RedisGeoCommands.GeoLocation<byte[]>> nearByXYByProtoStuff(Object k, Circle circle, RedisGeoCommands.GeoRadiusCommandArgs args) {
        byte[] key = redisTemplate.getKeySerializer().serialize(k);
        GeoResults<RedisGeoCommands.GeoLocation<byte[]>> raw = (GeoResults)redisTemplate.execute((connection) -> {
            return connection.geoRadius(key, circle, args);
        }, true);
        return raw;
    }


    /**
     * 根据指定的地点查询半径在指定范围内的位置,时间复杂度为O(log(N)+M)，N为指定半径范围内的元素个数，M为要返回的个数
     * redis命令：georadiusbymember cityGeo 北京 100 km WITHDIST WITHCOORD ASC COUNT 5
     *
     * @param k
     * @param mk
     * @param distance
     * @param args
     * @return
     */
    public GeoResults nearByPlace(Object k, Object mk, Distance distance, RedisGeoCommands.GeoRadiusCommandArgs args) {
        GeoResults<RedisGeoCommands.GeoLocation<String>> results = redisTemplate.opsForGeo().radius(k, mk, distance, args);
        return results;
    }

    /**
     * 返回的是geohash值,查找一个位置的时间复杂度为O(log(N))
     * redis命令：geohash cityGeo 北京
     *
     * @param k
     * @param mks
     * @return
     */
    public List geoHash(Object k, Object... mks) {
        List<String> results = redisTemplate.opsForGeo().hash(k, mks);
        return results;
    }
}