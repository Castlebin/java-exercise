package redis;

import java.util.Date;

import org.junit.Test;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import util.SystemUtil;

@Slf4j
public class RedisBigKeyTest {

    @Test
    public void initBigKey() {
        JedisPool pool = new JedisPool("localhost", 6379);
        Jedis jedis = pool.getResource();
        jedis.set("bigkey", new String(new char[50 * 1024 * 1024]));
    }

    @Test
    public void initSmallKey() {
        JedisPool pool = new JedisPool("localhost", 6379);
        Jedis jedis = pool.getResource();
        for (int i = 0; i < 1000; i++) {
            jedis.set("smallkey_" + i, new String(new char[1024]));
        }
    }

    @Test
    public void getSmallKey() {
        JedisPool pool = new JedisPool("localhost", 6379);
        Jedis jedis = pool.getResource();
        for (int i = 0; i < 100; i++) {
            jedis.get("smallkey_" + i);
            System.out.println(DateUtil.format(new Date(), DatePattern.UTC_MS_FORMAT) + " get smallkey_" + i);
            SystemUtil.sleep(10);
        }
    }

    @Test
    public void getBigKey() {
        JedisPool pool = new JedisPool("localhost", 6379);
        Jedis jedis = pool.getResource();
        for (int i = 0; i < 100000; i++) {
            jedis.get("bigkey");
            System.out.println(DateUtil.format(new Date(), DatePattern.UTC_MS_FORMAT) + " get bigkey");
        }
    }

}
