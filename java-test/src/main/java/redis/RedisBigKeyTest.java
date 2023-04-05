package redis;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import com.heller.util.SystemUtil;

import cn.hutool.log.Log;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisBigKeyTest {
    private final Log log = Log.get();

    // 本地启动一个 redis 服务，密码为空
    public static final String HOST = "localhost";
    public static final int PORT = 6379;
    // 没有用户名密码的时候，用户名密码是 null，而不是空字符串
    public static final String USERNAME = null;
    public static final String PASSWORD = null;

    private JedisPool initPool() {
        return new JedisPool(HOST, PORT, USERNAME, PASSWORD);
    }

    /**
     * 混合多线程执行大 key 和小 key 任务
     * 可以明显看到，小 key 查询耗时会受大 key 影响，有些甚至上升到上百毫秒!!（正常是毫秒级）
     */
    @Test
    public void taskKeys() throws Exception {
        try (JedisPool pool = initPool()) {
            int bigKeyThreadCount = 2;
            CountDownLatch bigKeyLatch = new CountDownLatch(bigKeyThreadCount);
            String bigKey = "bigkey";
            for (int count = 0; count < bigKeyThreadCount; count++) {
                new Thread(() -> {
                    try (Jedis jedis = pool.getResource()) {
                        for (int i = 0; i < 5; i++) {
                            String key = bigKey;
                            log.info("get " + key + " cost: " + SystemUtil.cost(() -> {
                                jedis.get(key);
                            }) + "ms " + Thread.currentThread().getName());
                        }
                    } finally {
                        bigKeyLatch.countDown();
                        if (bigKeyLatch.getCount() == 0) {
                            log.debug("big key task finished");
                        }
                    }
                }).start();
            }


            // 注意 jedisPool 和 jedis 都应该 close，回收资源
            int smallKeyThreadCount = 100;
            CountDownLatch smallKeyLatch = new CountDownLatch(smallKeyThreadCount);
            for (int count = 0; count < smallKeyThreadCount; count++) {
                new Thread(() -> {
                    // jedis 没有 close 的话，会导致连接池耗尽，后续的线程都会阻塞
                    try (Jedis jedis = pool.getResource()) {
                        for (int i = 0; i < 1000; i++) {
                            String key = "smallkey_" + (i % 100);
                            long cost = SystemUtil.cost(() -> {
                                jedis.get(key);
                            });
                            // log.info("get " + key + " cost: " + cost + "ms " + Thread.currentThread().getName());
                            if (cost > 10) {
                                // 可以看出来 小 key 的耗时会收到影响！！有的耗时甚至上百毫秒
                                log.warn("get " + key + " cost: " + cost + "ms " + Thread.currentThread().getName());
                            }
                        }
                    } finally {
                        smallKeyLatch.countDown();
                        if (smallKeyLatch.getCount() == 0) {
                            log.debug("small key task finished");
                        }
                    }
                }).start();
            }


            smallKeyLatch.await();
            bigKeyLatch.await();
        }
    }

    @Test
    public void initBigKey() {
        try (JedisPool pool = initPool()) {
            Jedis jedis = pool.getResource();
            System.out.println(SystemUtil.cost(() -> {
                jedis.set("bigkey", new String(new char[50 * 1024 * 1024]));
            }));
        }
    }

    @Test
    public void initSmallKey() {
        try (JedisPool pool = initPool()) {
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
        try (JedisPool pool = initPool()) {
            Jedis jedis = pool.getResource();
            for (int c = 0; c < 10; c++) {
                for (int i = 0; i < 10; i++) {
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
        try (JedisPool pool = initPool()) {
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

}
