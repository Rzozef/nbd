package org.pl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.pl.converters.ClientRedisConverter;
import org.pl.databaseModel.ClientRedis;
import org.pl.databaseRepository.ClientRedisRepository;
import org.pl.model.Address;
import org.pl.model.Basic;
import org.pl.model.Client;
import redis.clients.jedis.*;

import static org.junit.jupiter.api.Assertions.*;


import java.util.UUID;

public class redistest {
    private ClientRedisRepository crr;
    private Client client = new Client(UUID.randomUUID(), false, 10, "a", "b", "1", "1", new Basic(), new Address("a", "g", "b"), 0);

    @Test
    public void initConnection() throws JsonProcessingException {
        crr = new ClientRedisRepository();
        ClientRedis clientRedis = ClientRedisConverter.toRepositoryModel(client);
        Client client1 = client;
        client1.setArchive(true);
        ClientRedis clientRedis1 = ClientRedisConverter.toRepositoryModel(client1);
        crr.add(clientRedis);
        System.out.println(clientRedis.isArchive());
        crr.set(client.getEntityId().toString(), clientRedis1);
        System.out.println(crr.read(clientRedis.getEntityId().toString()).isArchive());
        crr.delete(client.getEntityId().toString());
        assertNull(crr.read(client.getEntityId().toString()));
        crr.delete(client.getEntityId().toString());
    }
}
