package org.pl.databaseRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.pl.databaseModel.ClientRedis;
import redis.clients.jedis.Jedis;

public class ClientRedisRepository extends RedisRepository {
    private ObjectMapper objectMapper;
    private final String hashPrefix = ":";

    public ClientRedisRepository() throws JsonProcessingException {
        initConnection();
        objectMapper = new ObjectMapper();
    }

    public void add(ClientRedis client) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(client);
        Jedis jedis = getPool().getResource();
        jedis.set(client.getEntityId().toString(), json);
    }

    public ClientRedis read(String key) throws JsonProcessingException {
        if (getPool().getResource().get(key) == null)
            return null;
        return objectMapper.readValue(getPool().getResource().get(key), ClientRedis.class);
    }

    public void set(String key, ClientRedis client) throws JsonProcessingException {
        getPool().getResource().set(key, objectMapper.writeValueAsString(client));
    }

    public void delete(String key) {
        getPool().getResource().del(key);
    }
}
