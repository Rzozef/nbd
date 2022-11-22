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

import static org.junit.jupiter.api.Assertions.*;

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
        client = new Client(UUID.randomUUID(), false, 10, "a", "b", "1", "1", new Basic(), new Address("a", "g", "b"), 0);
        client1 = new Client(UUID.randomUUID(), true, 20, "a", "b", "1", "1", new Basic(), new Address("a", "g", "b"), 0);
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
        UUID id = clientRedisService.getAllClients().get(0).getEntityId();
        assertNotNull(clientRedisService.getClient(id));
    }

    @Test
    void getAllClientsTest() throws RepositoryException, ClientException, JsonProcessingException {
        assertTrue(clientRedisService.add("Jan", "Kowalski", "997", "123", "Lodz", "12", "Dabrowskiego", "Basic"));
        assertTrue(clientRedisService.add("Jan", "Kowalski", "997", "123", "Lodz", "12", "Dabrowskiego", "Basic"));
        assertEquals(2, clientRedisService.getAllClients().size());
    }

    @Test
    void updateClientTest() throws RepositoryException, ClientException, JsonProcessingException {
        assertTrue(clientRedisService.add("Jan", "Kowalski", "997", "123", "Lodz", "12", "Dabrowskiego", "Basic"));
        UUID id = clientRedisService.getAllClients().get(0).getEntityId();
        System.out.println(clientRedisService.getClient(id).getID());
        clientRedisService.updateClient(id, client);
        assertEquals("a", clientRedisService.getClient(id).getFirstName());
        System.out.println(clientRedisService.getClient(id).getID());
    }

    @Test
    void deleteClientTest() throws RepositoryException, ClientException, JsonProcessingException {
        assertTrue(clientRedisService.add("Jan", "Kowalski", "997", "123", "Lodz", "12", "Dabrowskiego", "Basic"));
        UUID id = clientRedisService.getAllClients().get(0).getEntityId();
        clientRedisService.deleteClient(id);
        assertNull(clientRedisService.getClient(id));
    }

    @AfterEach
    void afterEach() {
        clientRedisService.deleteAll();
    }
}
