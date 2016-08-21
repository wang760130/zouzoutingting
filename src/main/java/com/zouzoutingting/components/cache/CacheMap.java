package com.zouzoutingting.components.cache;


/**
 * 缓存Map,根据简单的LRU算法
 * 
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2016年8月21日
 */
public class CacheMap<K, V> {
	
	/**
     * 默认缓存容量
     */
    private final static int DEFAULT_INIT_SIZE = 256;
    
    /**
     * 缓存失效时长(毫秒)
     */
    private final long expire;
    
    private LRUMap<K, Entry<V>> valueMap;

    /**
     * 带失效时长的构造方法
     *
     * @param expireMs 失效时长
     */
    public CacheMap(long updateInterval) {
        this.expire = updateInterval;
        valueMap = new LRUMap<K, Entry<V>>(DEFAULT_INIT_SIZE);
    }

    /**
     * 带失效时长和初始大小的构造方法
     *
     * @param updateInterval 失效时长
     * @param initSize 初始大小
     */
    public CacheMap(long updateInterval, int initSize) {
        this.expire = updateInterval;
        valueMap = new LRUMap<K, Entry<V>>(initSize);
    }

    /**
     * 从缓存中得到数据
     *
     * @param key
     * @return
     */
    public V get(K key) {
        Long acccessTime = System.currentTimeMillis();
        Entry<V> entry = valueMap.get(key);
        if (entry == null) {
            return null;
        } else if (acccessTime - entry.getTime() > expire) {
            valueMap.remove(key);
            return null;
        }
        return entry.getObj();
    }

    /**
     * 添加数据到缓存中
     *
     * @param key
     * @param value
     */
    public void put(K key, V value) {
        valueMap.put(key, new Entry<V>(value));
    }
    
    /**
     * 删除缓存中的数据
     * @param key
     */
    public void delete(K key) {
    	valueMap.remove(key);
    }

    /**
     * 未做同步的size方法。在多线程情况下获取的的值可能不准确。
     */
    public int size() {
        return valueMap.size();
    }

    public void clear() {
        valueMap.clear();
    }
    
    /**
     * 缓存中存放的实体
     */
    static class Entry<T> {
        /**
         * 最近访问时间
         */
        private long time;
        private T obj;

        /**
         * @param time
         * @param obj
         */
        private Entry(T obj) {
            this.time = System.currentTimeMillis();
            this.obj = obj;
        }

        /**
         * 更新最近访问时间为当前时间。
         */
        public void updateTime() {
            time = System.currentTimeMillis();
        }

        public long getTime() {
            return time;
        }

        public T getObj() {
            return obj;
        }

    }
}
