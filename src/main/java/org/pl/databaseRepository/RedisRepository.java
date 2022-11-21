package org.pl.databaseRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import redis.clients.jedis.*;

public class RedisRepository {
    private static JedisPool pool;

    protected void initConnection() throws JsonProcessingException {
        JedisClientConfig clientConfig = DefaultJedisClientConfig.builder().build();
        pool = new JedisPool(new HostAndPort("localhost", 6379), clientConfig);
    }

    public static JedisPool getPool() {
        return pool;
    }
}
