package com.heller;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import cn.hutool.log.Log;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class TestTask1 {

    private static final Log log = Log.get();

    @Test
    public void task1() {
        // 任务 1: 单线程获取大 key 值 20 次，查看耗时是多少
        String key = "bigkey";
        try (JedisPool pool = new JedisPool("localhost", 6379)) {
            CountDownLatch latch = new CountDownLatch(1);
            new Thread(() -> {
                try {
                    long start = System.currentTimeMillis();
                    Jedis jedis = pool.getResource();
                    for (int i = 0; i < 20; i++) {
                        jedis.get(key);
                    }
                    log.info("task 1 cost: " + (System.currentTimeMillis() - start) + "ms");
                } finally {
                    latch.countDown();
                }
            }).start();
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
