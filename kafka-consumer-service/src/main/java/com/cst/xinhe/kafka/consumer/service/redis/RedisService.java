package com.cst.xinhe.kafka.consumer.service.redis;

import java.util.concurrent.TimeUnit;

public interface RedisService {



    //key是否存在
    Boolean hasKey(String key);

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    String get(final String key);
    /**
     * 写入缓存
     */
    boolean set(final String key, String value, Long expireTime);

    boolean setNoTime(final String key, String value);

    /**
     * 更新缓存
     */
    boolean getAndSet(final String key, String value);

    /**
     * 更新缓存
     * 刷新缓存时间
     */
    boolean getAndSetExpireTime(final String key, String value, Long expireTime);

    /**
     * 删除缓存
     */
    boolean delete(final String key);
}
