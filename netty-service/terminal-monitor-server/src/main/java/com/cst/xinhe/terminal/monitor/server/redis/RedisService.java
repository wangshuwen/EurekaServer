package com.cst.xinhe.terminal.monitor.server.redis;

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
    boolean set(final String key, String value,Long expireTime);

    boolean setNoTime(final String key, String value);

    /**
     * 更新缓存
     */
    boolean getAndSet(final String key, String value);

    /**
     * 删除缓存
     */
    boolean delete(final String key);
}
