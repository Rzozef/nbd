package org.pl.databaseService;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.converters.ClientRedisConverter;
import org.pl.databaseModel.ClientRedis;
import org.pl.databaseRepository.ClientRedisRepository;
import org.pl.exceptions.ClientException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Address;
import org.pl.model.Basic;
import org.pl.model.Client;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientRedisServiceTest {
    private static ClientRedisRepository clientRedisRepository;
    private ClientRedisService clientRedisService;
    private Client client;
    private Client client1;
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
        clientRedisService = new ClientRedisService(clientRedisRepository);
    }

    @Test
    void addClientPositiveTest() {
        assertThrows(ClientException.class,
                ()-> clientRedisService.add("", "Kowalski", "997", "123", "Lodz", "12", "Dabrowskiego", "Basic"));
        assertThrows(ClientException.class,
                ()-> clientRedisService.add("Jan", "", "997", "123", "Lodz", "12", "Dabrowskiego", "Basic"));
        assertThrows(ClientException.class,
                ()-> clientRedisService.add("Jan", "Kowalski", "", "123", "Lodz", "12", "Dabrowskiego", "Basic"));
        assertThrows(ClientException.class,
                ()-> clientRedisService.add("Jan", "Kowalski", "997", "", "Lodz", "12", "Dabrowskiego", "Basic"));
        assertThrows(ClientException.class,
                ()-> clientRedisService.add("Jan", "Kowalski", "997", "123", "", "12", "Dabrowskiego", "Basic"));
        assertThrows(ClientException.class,
                ()-> clientRedisService.add("Jan", "Kowalski", "997", "123", "Lodz", "", "Dabrowskiego", "Basic"));
        assertThrows(ClientException.class,
                ()-> clientRedisService.add("Jan", "Kowalski", "997", "123", "Lodz", "12", "", "Basic"));
        assertThrows(ClientException.class,
                ()-> clientRedisService.add("Jan", "Kowalski", "997", "123", "Lodz", "12", "", ""));
        assertThrows(ClientException.class,
                ()-> clientRedisService.add("Jan", "Kowalski", "997", "123", "Lodz", "12", "", "Ksieciuniu"));
    }

    @Test
    void addPositiveTest() throws RepositoryException, ClientException, JsonProcessingException {
        assertTrue(clientRedisService.add("Jan", "Kowalski", "997", "123", "Lodz", "12", "Dabrowskiego", "Basic"));
    }

    @Test
    void getPositiveTest() throws RepositoryException, ClientException, JsonProcessingException {
        assertTrue(clientRedisService.add("Jan", "Kowalski", "997", "123", "Lodz", "12", "Dabrowskiego", "Basic"));
    }

    @AfterEach
    void afterEach() {
        clientRedisService.deleteAll();
    }
}
