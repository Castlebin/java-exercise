package redis;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import cn.hutool.log.Log;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import util.SystemUtil;

public class RedisBigKeyTest {
    private final Log log = Log.get();

    @Test
    public void initBigKey() {
        try (JedisPool pool = new JedisPool("localhost", 6379)) {
            Jedis jedis = pool.getResource();
            System.out.println(SystemUtil.cost(() -> {
                jedis.set("bigkey", new String(new char[50 * 1024 * 1024]));
            }));
        }
    }

    @Test
    public void initSmallKey() {
        try (JedisPool pool = new JedisPool("localhost", 6379)) {
            Jedis jedis = pool.getResource();
            int count = 100;
            System.out.println(SystemUtil.cost(() -> {
                for (int i = 0; i < count; i++) {
                    String key = "smallkey_" + (100 + i);
                    jedis.set(key, new String(new char[1024]));
                }
            }) * 1.0 / count);
        }
    }

    @Test
    public void getSmallKey() {
        try (JedisPool pool = new JedisPool("localhost", 6379)) {
            Jedis jedis = pool.getResource();
            for (int c = 0; c < 10; c++) {
                for (int i = 0; i < 100; i++) {
                    String key = "smallkey_" + (100 + i);
                    System.out.println("get " + key + " cost: " + SystemUtil.cost(() -> {
                        jedis.get(key);
                    }) + "ms");
                }
            }
        }
    }

    @Test
    public void getBigKey() {
        try (JedisPool pool = new JedisPool("localhost", 6379)) {
            Jedis jedis = pool.getResource();
            for (int i = 0; i < 10; i++) {
                String key = "bigkey";
                jedis.get(key);
                System.out.println("get " + key + " cost: " + SystemUtil.cost(() -> {
                    jedis.get(key);
                }) + "ms");
            }
        }
    }

    /**
     * 模拟实际情况，多个线程分别获取大 key 值和小 key 值，
     * 看看获取大 key 值对获取小 key 值是否有影响
     */
    @Test
    public void getBigKeySmallKeys() {
        try (JedisPool pool = new JedisPool("localhost", 6379)) {
            int threadCount = 8;
            CountDownLatch latch = new CountDownLatch(threadCount);
            int bigKeyCount = 2;
            for (int count = 0; count < bigKeyCount; count++) {
                new Thread(() -> {
                    // 注意，jedis 是非线程安全的，每个线程都要获取一个新的 jedis 对象
                    Jedis jedis = pool.getResource();
                    for (int i = 0; i < 100; i++) {
                        String key = "bigkey";
                        log.info("get " + key + " cost: " + SystemUtil.cost(() -> {
                            jedis.get(key);
                        }) + "ms" + " thread: " + Thread.currentThread().getName());
                    }
                    latch.countDown();
                }).start();
            }
            for (int count = 0; count < threadCount - bigKeyCount; count++) {
                new Thread(() -> {
                    Jedis jedis = pool.getResource();
                    for (int c = 0; c < 100; c++) {
                        for (int i = 0; i < 100; i++) {
                            String key = "smallkey_" + (100 + i);
                            long cost = SystemUtil.cost(() -> {
                                jedis.get(key);
                            });
                            log.info(("get " + key + " cost: " + cost + "ms" + " thread: " + Thread.currentThread()
                                    .getName()));
                            if (cost > 5) {
                                log.warn("get " + key + " cost: " + cost + "ms" + " thread: " + Thread.currentThread()
                                        .getName());
                            }
                        }
                    }
                    latch.countDown();
                }).start();
            }
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
