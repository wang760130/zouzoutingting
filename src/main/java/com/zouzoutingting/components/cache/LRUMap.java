package com.zouzoutingting.components.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 一个限定容量的Map
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2016年8月21日
 */
public class LRUMap<K, V> extends LinkedHashMap<K, V>{
	private static final long serialVersionUID = 1L;
    private final int maxCapacity;
    // 这个map不会扩容
    private static final float LOAD_FACTOR = 0.99f;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public LRUMap(int maxCapacity) {
        super(maxCapacity, LOAD_FACTOR, false);
        this.maxCapacity = maxCapacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maxCapacity;
    }

    @Override
    public V get(Object key) {
        try {
            lock.readLock().lock();
            return super.get(key);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public V put(K key, V value) {
        try {
            lock.writeLock().lock();
            return super.put(key, value);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public V remove(Object key) {
        try {
            lock.writeLock().lock();
            return super.remove(key);
        } finally {
            lock.writeLock().unlock();
        }

    }

    @Override
    public void clear() {
        try {
            lock.writeLock().lock();
            super.clear();
        } finally {
            lock.writeLock().unlock();
        }

    }
}
