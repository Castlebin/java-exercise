package redis;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import cn.hutool.log.Log;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisBigKeyTasks {
    private final Log log = Log.get();

    @Test
    public void getBigKeyTask_1() {
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

    @Test
    public void getBigKeyTask_2() {
        // 任务 2: 5 个线程分别获取大 key 值 4 次，共 20 次，查看耗时是多少
        String key = "bigkey";
        int threadCount = 5;
        CountDownLatch latch = new CountDownLatch(threadCount);
        try (JedisPool pool = new JedisPool("localhost", 6379)) {
            long start = System.currentTimeMillis();
            for (int count = 0; count < threadCount; count++) {
                new Thread(() -> {
                    try {
                        // 注意，jedis 是非线程安全的，每个线程都要获取一个新的 jedis 对象
                        Jedis jedis = pool.getResource();
                        for (int i = 0; i < 4; i++) {
                            jedis.get(key);
                        }
                    } finally {
                        latch.countDown();
                    }
                }).start();
            }
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("task 2 cost: " + (System.currentTimeMillis() - start) + "ms");
        }
    }

}
