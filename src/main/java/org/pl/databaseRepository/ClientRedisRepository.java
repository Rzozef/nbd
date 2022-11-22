package org.pl.databaseRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.pl.databaseModel.ClientRedis;

public class ClientRedisRepository extends RedisRepository {
    private ObjectMapper objectMapper;
    private final String hashPrefix = ":";

    public ClientRedisRepository() throws JsonProcessingException {
        initConnection();
        objectMapper = new ObjectMapper();
    }

    public void add(ClientRedis client) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(client);
        getPool().set(hashPrefix + client.getEntityId(), json);
    }

    public ClientRedis read(String key) throws JsonProcessingException {
        if (getPool().get(hashPrefix + key) == null)
            return null;
        return objectMapper.readValue(getPool().get(hashPrefix + key), ClientRedis.class);
    }

    public void set(String key, ClientRedis client) throws JsonProcessingException {
        getPool().set(hashPrefix + key, objectMapper.writeValueAsString(client));
    }

    public boolean delete(String key) throws JsonProcessingException {
        if (read(key) != null) {
            getPool().del(hashPrefix + key);
            return true;
        }
        return false;
    }
}
