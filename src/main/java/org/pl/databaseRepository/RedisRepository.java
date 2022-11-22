package org.pl.databaseRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import redis.clients.jedis.*;

public class RedisRepository {
    private static JedisPooled pool;
    private static Jedis jedis;

    protected void initConnection() throws JsonProcessingException {
        JedisClientConfig clientConfig = DefaultJedisClientConfig.builder().build();
        pool = new JedisPooled(new HostAndPort("localhost", 6379), clientConfig);
        jedis = new Jedis("localhost", 6379);
    }

    public static JedisPooled getPool() {
        return pool;
    }

    public static Jedis getJedis() {
        return jedis;
    }
}
