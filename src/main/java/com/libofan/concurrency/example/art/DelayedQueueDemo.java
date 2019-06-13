package com.libofan.concurrency.example.art;

import java.sql.Time;
import java.util.HashMap;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 缓存系统：检测缓存是否过期，如果能查到就代表过期了;
 */

public class DelayedQueueDemo {
    // 缓存类, 构造这个类就启动这个类
    // 这个线程来维护DelayQueue对象
    static class Cache<K, V> implements Runnable {
        private ConcurrentHashMap<K, V> itemMap = new ConcurrentHashMap<>();

        private DelayQueue<CacheItem<K>> delayQueue = new DelayQueue<>();

        public void put(K key, V value) {
            return;

        }

        public V get(K value) {
            return null;
        }

        public Cache() {
            new Thread(this).start();
        }

        public boolean stop = false;

        @Override
        public void run() {
            while (!stop) {
                CacheItem cacheItem = delayQueue.poll();
                if (cacheItem != null) {
                    itemMap.remove(cacheItem.getKey());
                }
            }
        }

        public void setStop(boolean stop) {
            this.stop = stop;
        }
    }

    static class CacheItem<K> implements Delayed {
        /**
         * @param key 缓存的对象
         * @param expireTime 缓存对象的过期时间
         */
        public CacheItem(K key, long expireTime) {
            this.key = key;
            this.expireTime = expireTime;
            this.createTime = System.currentTimeMillis();
        }

        private K key;

        // 过期时间段
        private long expireTime;

        // 创建时间
        private long createTime;

        @Override
        public int compareTo(Delayed o) {
            // 统一使用的时间单元
            TimeUnit unionTimeUnit = TimeUnit.MILLISECONDS;
            // 剩余过期时间长的放在队列尾部
            if (o.getDelay(unionTimeUnit) < this.getDelay(unionTimeUnit)) {
                return 1;
            }
            if (o.getDelay(unionTimeUnit) > this.getDelay(unionTimeUnit)) {
                return -1;
            }
            return 0;
        }

        // 返回在给定time unit情况下剩余的关联到这个对象的delay, 大于0说明未过期
        @Override
        public long getDelay(TimeUnit unit) {
            return expireTime - unit.toSeconds(System.currentTimeMillis() - createTime);
        }

        public K getKey() {
            return key;
        }
    }

    public static void main(String[] args) {

    }
}
