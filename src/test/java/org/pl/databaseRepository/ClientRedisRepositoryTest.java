package org.pl.databaseRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.*;
import org.pl.converters.ClientRedisConverter;
import org.pl.databaseModel.ClientRedis;
import org.pl.exceptions.ClientException;
import org.pl.model.Address;
import org.pl.model.Basic;
import org.pl.model.Client;

import static org.junit.jupiter.api.Assertions.*;


import java.util.UUID;

public class ClientRedisRepositoryTest {
    private static ClientRedisRepository clientRedisRepository;
    private ClientRedis clientRedis;
    private ClientRedis clientRedis1;
    @BeforeAll
    static void initConnection() throws JsonProcessingException {
        clientRedisRepository = new ClientRedisRepository();
    }

    @BeforeEach
    void setUp() {
        Client client = new Client(UUID.randomUUID(), false, 10, "a", "b", "1", "1", new Basic(), new Address("a", "g", "b"), 0);
        Client client1 = new Client(UUID.randomUUID(), true, 20, "a", "b", "1", "1", new Basic(), new Address("a", "g", "b"), 0);
        clientRedis = ClientRedisConverter.toRepositoryModel(client);
        clientRedis1 = ClientRedisConverter.toRepositoryModel(client1);
    }

    @Test
    void addClientPositiveTest() throws JsonProcessingException {
        assertTrue(clientRedisRepository.add(clientRedis));
        assertNotNull(clientRedisRepository.read(clientRedis.getEntityId().toString()));
    }

    @Test
    void addClientNegativeTest() throws JsonProcessingException {
        assertTrue(clientRedisRepository.add(clientRedis));
        assertFalse(clientRedisRepository.add(clientRedis));
    }

    @Test
    void readClientPositiveTest() throws JsonProcessingException {
        assertTrue(clientRedisRepository.add(clientRedis));
        assertEquals(clientRedis.getEntityId(), clientRedisRepository.read(clientRedis.getEntityId().toString()).getEntityId());
    }

    @Test
    void readAllClientsTest() throws JsonProcessingException {
        assertTrue(clientRedisRepository.add(clientRedis));
        assertTrue(clientRedisRepository.add(clientRedis1));
        assertEquals(2, clientRedisRepository.readAllClients().size());
    }

    @Test
    void readClientNegativeTest() throws JsonProcessingException {
        assertNull(clientRedisRepository.read(clientRedis.getEntityId().toString()));
    }

    @Test
    void updateClientTest() throws JsonProcessingException, ClientException {
        assertTrue(clientRedisRepository.add(clientRedis));
        assertEquals(clientRedis.getEntityId(), clientRedisRepository.set(clientRedis.getEntityId().toString(), clientRedis1).getEntityId());
        assertNotEquals(clientRedis, clientRedisRepository.set(clientRedis.getEntityId().toString(), clientRedis1));
    }

    @Test
    void deleteClientPositiveTest() throws JsonProcessingException {
        assertTrue(clientRedisRepository.add(clientRedis));
        assertNotNull(clientRedisRepository.read(clientRedis.getEntityId().toString()));
        assertTrue(clientRedisRepository.delete(clientRedis.getEntityId().toString()));
        assertNull(clientRedisRepository.read(clientRedis.getEntityId().toString()));
    }

    @Test
    void deleteClientNegativeTest() throws JsonProcessingException {
        assertFalse(clientRedisRepository.delete(clientRedis.getEntityId().toString()));
    }

    @Test
    void deleteAllClientsTest() throws JsonProcessingException {
        assertTrue(clientRedisRepository.add(clientRedis));
        assertTrue(clientRedisRepository.add(clientRedis1));
        assertNotNull(clientRedisRepository.read(clientRedis.getEntityId().toString()));
        assertNotNull(clientRedisRepository.read(clientRedis1.getEntityId().toString()));
        clientRedisRepository.deleteAll();
        assertNull(clientRedisRepository.read(clientRedis.getEntityId().toString()));
        assertNull(clientRedisRepository.read(clientRedis1.getEntityId().toString()));
    }

    @Test
    void isConnectedPositiveTest() {
        assertTrue(clientRedisRepository.isConnected());
    }

    @AfterEach
    void afterEach() {
        clientRedisRepository.deleteAll();
    }
}
