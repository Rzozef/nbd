package org.pl.databaseRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.pl.converters.ClientRedisConverter;
import org.pl.databaseModel.ClientRedis;
import org.pl.exceptions.ClientException;
import org.pl.model.Client;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ClientRedisRepository extends RedisRepository {
    private ObjectMapper objectMapper;
    private final String hashPrefix = ":";

    public ClientRedisRepository() throws JsonProcessingException {
        initConnection();
        objectMapper = new ObjectMapper();
    }

    public boolean add(ClientRedis client) throws JsonProcessingException {
        if (getPool().get(hashPrefix + client.getEntityId().toString()) == null) {
            String json = objectMapper.writeValueAsString(client);
            getPool().set(hashPrefix + client.getEntityId(), json);
            return true;
        }
        return false;
    }

    public ClientRedis read(String key) throws JsonProcessingException {
        if (getPool().get(hashPrefix + key) == null)
            return null;
        return objectMapper.readValue(getPool().get(hashPrefix + key), ClientRedis.class);
    }

    public List<ClientRedis> readAllClients() throws JsonProcessingException {
        Set<String> setStringKeys = getJedis().keys("*");
        List<String> listStringKeys = new ArrayList<>(setStringKeys);
        List<ClientRedis> clientsRedis = new ArrayList<>();
        for (int i = 0; i < setStringKeys.size(); i++) {
            clientsRedis.add(objectMapper.readValue(getPool().get(listStringKeys.get(i)), ClientRedis.class));
        }
        return clientsRedis;
    }

    public ClientRedis set(String key, ClientRedis clientRedis) throws JsonProcessingException, ClientException {
        Client client = ClientRedisConverter.fromRepositoryModelClient(clientRedis);
        Client client1 = ClientRedisConverter.fromRepositoryModelClient(read(key));
        client1.setArchive(client.isArchive());
        client1.setBalance(client.getBalance());
        client1.setFirstName(client.getFirstName());
        client1.setLastName(client.getLastName());
        client1.setPhoneNumber(client.getPhoneNumber());
        client1.setClientType(client.getClientType());
        client1.setAddress(client.getAddress());
        if (client.getRepairs() > client.getClientType().getMaxRepairs()) {
            throw new ClientException(ClientException.CLIENT_MAX_REPAIRS_EXCEEDED);
        }
        client1.setRepairs(client.getRepairs());
        getPool().set(hashPrefix + key, objectMapper.writeValueAsString(ClientRedisConverter.toRepositoryModel(client1)));
        return objectMapper.readValue(getPool().get(hashPrefix + key), ClientRedis.class);
    }

    public boolean delete(String key) throws JsonProcessingException {
        if (read(key) != null) {
            getPool().del(hashPrefix + key);
            return true;
        }
        return false;
    }

    public void deleteAll() {
        if (isConnected())
            getJedis().flushDB();
    }

    public boolean isConnected() {
        boolean isConnected;
        try {
            isConnected = getJedis().ping().equalsIgnoreCase("pong");
        } catch (JedisConnectionException ex) {
            isConnected = false;
        }
        return isConnected;
    }
}
