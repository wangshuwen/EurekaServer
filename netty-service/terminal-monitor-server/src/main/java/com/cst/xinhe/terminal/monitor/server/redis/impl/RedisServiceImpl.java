package com.cst.xinhe.terminal.monitor.server.redis.impl;

import com.cst.xinhe.terminal.monitor.server.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-05-31 10:12
 **/
@Component
public class RedisServiceImpl implements RedisService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //key是否存在
    public Boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public String get(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 写入缓存
     */
    public boolean set(final String key, String value,Long expireTime) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key,value,expireTime, TimeUnit.HOURS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    @Override
    public boolean setNoTime(String key, String value) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key,value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 更新缓存
     */
    public boolean getAndSet(final String key, String value) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().getAndSet(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 删除缓存
     */
    public boolean delete(final String key) {
        boolean result = false;
        try {
            redisTemplate.delete(key);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
