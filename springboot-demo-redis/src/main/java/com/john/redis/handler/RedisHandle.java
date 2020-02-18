package com.john.redis.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Component
public class RedisHandle{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public RedisTemplate redisTemplate;

    public Set<String> getAllKeys() {
        return redisTemplate.keys("*");
    }

    public long addList(String key, Object obj) {
        return redisTemplate.boundListOps(key).rightPush(obj);
    }

    public long addList(String key, Object obj, long time) {
        long l = redisTemplate.boundListOps(key).rightPush(obj);
        redisTemplate.boundHashOps(key).expire(time, TimeUnit.SECONDS);
        return l;
    }

    public long addLeftList(String key, Object obj) {
        return redisTemplate.boundListOps(key).leftPush(obj);
    }

 	  
    public long addList(String key, Object... obj) {
        return redisTemplate.boundListOps(key).rightPushAll(obj);
    }

 	  
    public List<Object> getList(String key, long s, long e) {
        return redisTemplate.boundListOps(key).range(s, e);
    }

 	  
    public List<Object> getList(String key) {
        return redisTemplate.boundListOps(key).range(0, getListSize(key));
    }

 	  
    public long getListSize(String key) {
        return redisTemplate.boundListOps(key).size();
    }

    public long removeListValue(String key, Object object) {
        return redisTemplate.boundListOps(key).remove(0, object);
    }

    public void remove(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                remove(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

 	 public Boolean renameIfAbsent(String oldKey, String newKey) {
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }

 	  
 	 public void removeBlear(String blear) {
        redisTemplate.delete(redisTemplate.keys(blear));
    }


 	 public Long removeZSetValue(String key, Object... value) {
        return redisTemplate.boundZSetOps(key).remove(value);
    }

 	  

 	 public void removeZSetRange(String key, Long start, Long end) {
        redisTemplate.boundZSetOps(key).removeRange(start, end);
    }

 	  
 	 public void setZSetUnionAndStore(String key, String key1, String key2) {
        redisTemplate.boundZSetOps(key).unionAndStore(key1, key2);
    }

 	  
 	 public Set<Object> getZSetRange(String key) {
        return getZSetRange(key, 0, getZSetSize(key));
    }

 	  
 	 public Set<Object> getZSetRange(String key, long s, long e) {
        return redisTemplate.boundZSetOps(key).range(s, e);
    }

 	  
 	 public Set<Object> getZSetReverseRange(String key) {
        return getZSetReverseRange(key, 0, getZSetSize(key));
    }

 	  
 	 public Set<Object> getZSetReverseRange(String key, long start, long end) {
        return redisTemplate.boundZSetOps(key).reverseRange(start, end);
    }

 	  
 	 public Set<Object> getZSetRangeByScore(String key, double start, double end) {
        return redisTemplate.boundZSetOps(key).rangeByScore(start, end);
    }

 	  
 	 public Set<Object> getZSetReverseRangeByScore(String key, double start, double end) {
        return redisTemplate.boundZSetOps(key).reverseRangeByScore(start, end);
    }

 	  
 	 public Set<ZSetOperations.TypedTuple<Object>> getZSetRangeWithScores(String key, long start, long end) {
        return redisTemplate.boundZSetOps(key).rangeWithScores(start, end);
    }

 	  
 	 public Set<ZSetOperations.TypedTuple<Object>> getZSetReverseRangeWithScores(String key, long start, long end) {
        return redisTemplate.boundZSetOps(key).reverseRangeWithScores(start, end);
    }

 	  
 	 public Set<ZSetOperations.TypedTuple<Object>> getZSetRangeWithScores(String key) {
        return getZSetRangeWithScores(key, 0, getZSetSize(key));
    }

 	  
 	 public Set<ZSetOperations.TypedTuple<Object>> getZSetReverseRangeWithScores(String key) {
        return getZSetReverseRangeWithScores(key, 0, getZSetSize(key));
    }

 	  
 	 public long getZSetCountSize(String key, double sMin, double sMax) {
        return redisTemplate.boundZSetOps(key).count(sMin, sMax);
    }

 	  
 	 public long getZSetSize(String key) {
        return redisTemplate.boundZSetOps(key).size();
    }

 	  
 	 public double getZSetScore(String key, Object value) {
        return redisTemplate.boundZSetOps(key).score(value);
    }

 	  
 	 public double incrementZSetScore(String key, Object value, double delta) {
        return redisTemplate.boundZSetOps(key).incrementScore(value, delta);
    }

 	  
 	 public Boolean addZSet(String key, double score, Object value) {
        return redisTemplate.boundZSetOps(key).add(value, score);
    }

 	  
 	 public Long addZSet(String key, TreeSet<Object> value) {
        return redisTemplate.boundZSetOps(key).add(value);
    }

 	  
 	 public Boolean addZSet(String key, double[] score, Object[] value) {
        if (score.length != value.length) {
            return false;
        }
        for (int i = 0; i < score.length; i++) {
            if (addZSet(key, score[i], value[i]) == false) {
                return false;
            }
        }
        return true;
    }

 	  
 	 public void remove(String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

 	  
 	 public void removeZSetRangeByScore(String key, double s, double e) {
        redisTemplate.boundZSetOps(key).removeRangeByScore(s, e);
    }

 	  
 	 public Boolean setSetExpireTime(String key, Long time) {
        return redisTemplate.boundSetOps(key).expire(time, TimeUnit.SECONDS);
    }

 	  
 	 public Boolean setZSetExpireTime(String key, Long time) {
        return redisTemplate.boundZSetOps(key).expire(time, TimeUnit.SECONDS);
    }

 	  
 	 public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

 	 public Object get(int key) {
        return this.get(String.valueOf(key));
    }

 	 public Object get(long key) {
        return this.get(String.valueOf(key));
    }

 	  
 	 public Object get(String key) {
        return redisTemplate.boundValueOps(key).get();
    }

 	 public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

 	  
 	 public List<Object> get(List<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

 	  
 	 public List<Object> getByRegular(String regKey) {
        Set<String> stringSet = getAllKeys();
        List<Object> objectList = new ArrayList<Object>();
        for (String s : stringSet) {
            if (Pattern.compile(regKey).matcher(s).matches() && getType(s) == DataType.STRING) {
                objectList.add(get(s));
            }
        }
        return objectList;
    }

 	 public void set(long key, Object value) {
        this.set(String.valueOf(key), value);
    }

 	 public void set(int key, Object value) {
        this.set(String.valueOf(key), value);
    }

 	  
 	 public void multiSet(Map<String,Object> map) {
        redisTemplate.opsForValue().multiSet(map);
    }

 	  
 	 public void set(String key, Object value) {
        redisTemplate.boundValueOps(key).set(value);
    }

 	  
 	 public void set(String key, Object value, Long expireTime) {
        redisTemplate.boundValueOps(key).set(value, expireTime, TimeUnit.SECONDS);
    }

 	  
 	 public boolean setExpireTime(String key, Long expireTime) {
        return redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
    }

 	  
 	 public DataType getType(String key) {
        return redisTemplate.type(key);
    }

 	  
 	 public void removeMapField(String key, Object... field) {
        redisTemplate.boundHashOps(key).delete(field);
    }

 	  
 	 public Long getMapSize(String key) {
        return redisTemplate.boundHashOps(key).size();
    }

 	  
 	 public Map<String, Object> getMap(String key) {
        return redisTemplate.boundHashOps(key).entries();
    }

 	  
 	 public <T> T getMapField(String key, String field) {
        return (T) redisTemplate.boundHashOps(key).get(field);
    }

 	 public List<Object> getMapFields(String key, Collection keys) {
        return redisTemplate.boundHashOps(key).multiGet(keys);
    }

 	  
 	 public Boolean hasMapKey(String key, String field) {
        return redisTemplate.boundHashOps(key).hasKey(field);
    }

 	  
 	 public List<Object> getMapFieldValue(String key) {
        return redisTemplate.boundHashOps(key).values();
    }

 	  
 	 public Set<Object> getMapFieldKey(String key) {
        return redisTemplate.boundHashOps(key).keys();
    }

 	  
 	 public void addMap(String key, Map<String, Object> map) {
        redisTemplate.boundHashOps(key).putAll(map);
    }

 	 public void addMap(String key, Map<String, Object> map, long time) {
        redisTemplate.boundHashOps(key).putAll(map);
        redisTemplate.boundHashOps(key).expire(time, TimeUnit.SECONDS);
    }

 	 
 	 public long hincrby(String key, String field, long value) {
        return redisTemplate.boundHashOps(key).increment(field, value);
    }

 	  
 	 public long incrby(String key, long value) {
        return redisTemplate.boundValueOps(key).increment(value);
    }

 	 public long hincrby(String key, long value, long expiredTime) {
        Long increment = redisTemplate.boundValueOps(key).increment(value);
        redisTemplate.boundValueOps(key).expire(expiredTime, TimeUnit.SECONDS);
        return increment;
    }

 	  
 	 public void addMap(String key, String field, Object value) {
        redisTemplate.boundHashOps(key).put(field, value);
    }

 	  
 	 public void addMap(String key, String field, Object value, long time) {
        redisTemplate.boundHashOps(key).put(field, value);
        redisTemplate.boundHashOps(key).expire(time, TimeUnit.SECONDS);
    }

 	  
 	 public void watch(String key) {
        redisTemplate.watch(key);
    }

 	  
 	 public void addSet(String key, Object... obj) {
        redisTemplate.boundSetOps(key).add(obj);
    }

 	 public void addSet(String key, Object obj, long time) {
        redisTemplate.boundSetOps(key).add(obj);
        redisTemplate.boundHashOps(key).expire(time, TimeUnit.SECONDS);
    }

 	  
 	 public long removeSetValue(String key, Object obj) {
        return redisTemplate.boundSetOps(key).remove(obj);
    }

 	  
 	 public long removeSetValue(String key, Object... obj) {
        if (obj != null && obj.length > 0) {
            return redisTemplate.boundSetOps(key).remove(obj);
        }
        return 0L;
    }

 	  
    public long getSetSize(String key) {
        return redisTemplate.boundSetOps(key).size();
    }

 	  
    public Boolean hasSetValue(String key, Object obj) {
        Boolean boo = null;
        int t = 0;
        while (true) {
            try {
                boo = redisTemplate.boundSetOps(key).isMember(obj);
                break;
            } catch (Exception e) {
                logger.error("key[" + key + "],obj[" + obj + "]判断Set中的值是否存在失败,异常信息:" + e.getMessage());
                t++;
            }
        }
        logger.info("key[" + key + "],obj[" + obj + "]是否存在,boo:" + boo);
        return boo;
    }

 	  
    public Set<Object> getSet(String key) {
        return redisTemplate.boundSetOps(key).members();
    }

 	  
    public Set<Object> getSetUnion(String key, String otherKey) {
        return redisTemplate.boundSetOps(key).union(otherKey);
    }

 	  
 	 public Set<Object> getSetUnion(String key, Set<Object> set) {
        return redisTemplate.boundSetOps(key).union(set);
    }

 	  
 	 public Set<Object> getSetIntersect(String key, String otherKey) {
        return redisTemplate.boundSetOps(key).intersect(otherKey);
    }

 	  
 	 public Set<Object> getSetIntersect(String key, Set<Object> set) {
        return redisTemplate.boundSetOps(key).intersect(set);
    }

    public Cursor<String> scan(String pattern, int limit) {
        ScanOptions options = ScanOptions.scanOptions().match(pattern).count(limit).build();
        RedisSerializer<String> redisSerializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();
        return (Cursor) redisTemplate.executeWithStickyConnection(redisConnection -> new ConvertingCursor<>(redisConnection.scan(options), redisSerializer::deserialize));
    }

    public Long delete(List<String> keys){
        return redisTemplate.delete(keys);
    }

}