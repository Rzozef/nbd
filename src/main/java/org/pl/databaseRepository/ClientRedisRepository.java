package org.pl.databaseRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.pl.converters.ClientRedisConverter;
import org.pl.databaseModel.ClientRedis;
import org.pl.databaseService.ClientMongoService;
import org.pl.exceptions.ClientException;
import redis.clients.jedis.Jedis;

import java.util.UUID;

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

    public ClientRedis read(String key) throws JsonProcessingException, ClientException {
        if (getPool().getResource().ping().equalsIgnoreCase("pong")) {
            if (getPool().getResource().get(key) == null)
                return null;
            return objectMapper.readValue(getPool().getResource().get(key), ClientRedis.class);
        }
        else {
            ClientMongoService cms = new ClientMongoService(new ClientMongoRepository());
            return ClientRedisConverter.toRepositoryModel(cms.getClient(UUID.fromString(key)));
        }
    }

    public void set(String key, ClientRedis client) throws JsonProcessingException {
        getPool().getResource().set(key, objectMapper.writeValueAsString(client));
    }

    public boolean delete(String key) throws ClientException, JsonProcessingException {
        if (read(key) != null) {
            getPool().getResource().del(key);
            return true;
        }
        return false;
    }
}
