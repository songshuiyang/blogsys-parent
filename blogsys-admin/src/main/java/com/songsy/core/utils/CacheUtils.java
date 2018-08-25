package com.songsy.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

/**
 * 缓存工具类
 * @author songshuiyang
 * @date 2018/3/24 12:16
 */
public class CacheUtils {

    private final static Logger logger = LoggerFactory.getLogger(CacheUtils.class);

    private static CacheManager cacheManager = (CacheManager) SpringContextUtils.getBean("cacheManager");

    /**
     * 获取缓存
     * @param cacheName 缓存名
     * @param key 键
     * @return Object
     */
    public static Object get(String cacheName, Object key) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            //这里需要判断是否null
            if(cache.get(key) != null){
                return cache.get(key).get();
            }
        }
        return null;
    }
    /**
     * 添加缓存
     * 存在则更新
     * @param cacheName 缓存名
     * @param key 键
     * @param value 值
     */
    public static void put(String cacheName, Object key, Object value) {
        Cache cache = cacheManager.getCache(cacheName);
        if(cache != null){
            cache.put(key, value);
        }
    }
    /**
     * 清除缓存
     * @param cacheName 缓存名
     * @param key 键
     */
    public static void remove(String cacheName, Object key) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.evict(key);
        }else{
            logger.warn("this key is not in Cache");
        }
    }
}
