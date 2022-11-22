package org.pl.databaseService;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.converters.ClientTypeConverter;
import org.pl.databaseRepository.ClientMongoRepository;
import org.pl.exceptions.ClientException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Address;
import org.pl.model.Client;
import org.pl.model.Vip;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ClientMongoServiceTest {
    private static ClientMongoService clientMongoService;
    private static ClientMongoRepository clientMongoRepository;

    @BeforeAll
    static void initConnection() throws JsonProcessingException {
        clientMongoRepository = new ClientMongoRepository();
        clientMongoService = new ClientMongoService(clientMongoRepository);
    }

    @Test
    void addNegativeTest() {
        assertThrows(ClientException.class,
                ()-> clientMongoService.add("", "Kowalski", "997", "123", "Lodz", "12", "Dabrowskiego", "Basic"));
        assertThrows(ClientException.class,
                ()-> clientMongoService.add("Jan", "", "997", "123", "Lodz", "12", "Dabrowskiego", "Basic"));
        assertThrows(ClientException.class,
                ()-> clientMongoService.add("Jan", "Kowalski", "", "123", "Lodz", "12", "Dabrowskiego", "Basic"));
        assertThrows(ClientException.class,
                ()-> clientMongoService.add("Jan", "Kowalski", "997", "", "Lodz", "12", "Dabrowskiego", "Basic"));
        assertThrows(ClientException.class,
                ()-> clientMongoService.add("Jan", "Kowalski", "997", "123", "", "12", "Dabrowskiego", "Basic"));
        assertThrows(ClientException.class,
                ()-> clientMongoService.add("Jan", "Kowalski", "997", "123", "Lodz", "", "Dabrowskiego", "Basic"));
        assertThrows(ClientException.class,
                ()-> clientMongoService.add("Jan", "Kowalski", "997", "123", "Lodz", "12", "", "Basic"));
        assertThrows(ClientException.class,
                ()-> clientMongoService.add("Jan", "Kowalski", "997", "123", "Lodz", "12", "", ""));
        assertThrows(ClientException.class,
                ()-> clientMongoService.add("Jan", "Kowalski", "997", "123", "Lodz", "12", "", "Ksieciuniu"));
    }

    @Test
    void addPositiveTest() throws RepositoryException, ClientException, JsonProcessingException {
        assertTrue(clientMongoService.add("Jan", "Kowalski", "997", "123", "Lodz", "12", "Dabrowskiego", "Basic"));
    }

    @Test
    void getAllAddressesTest() throws RepositoryException, ClientException, JsonProcessingException {
        assertEquals(0, clientMongoService.getAllAddresses().size());
        assertTrue(clientMongoService.add("Jan", "Kowalski", "997", "123", "Lodz", "12", "Dabrowskiego", "Basic"));
        assertEquals(1, clientMongoService.getAllAddresses().size());
        assertEquals(Address.class, clientMongoService.getAllAddresses().get(0).getClass());
    }

    @Test
    void getClientTest() throws RepositoryException, ClientException, JsonProcessingException {
        assertTrue(clientMongoService.add("Jan", "Kowalski", "997", "123", "Lodz", "12", "Dabrowskiego", "Basic"));
        UUID id = clientMongoService.getAllClients().get(0).getEntityId();
        assertEquals(Client.class, clientMongoService.getClient(id).getClass());
        assertEquals("123", clientMongoService.getClient(id).getPersonalId());
    }

    @Test
    void getClientsAddress() throws RepositoryException, ClientException, JsonProcessingException {
        assertTrue(clientMongoService.add("Jan", "Kowalski", "997", "123", "Lodz", "12", "Dabrowskiego", "Basic"));
        UUID id = clientMongoService.getAllClients().get(0).getEntityId();
        assertEquals(Address.class, clientMongoService.getAddress(id).getClass());
        assertEquals("Lodz", clientMongoService.getAddress(id).getCity());
        assertEquals("12", clientMongoService.getAddress(id).getNumber());
        assertEquals("Dabrowskiego", clientMongoService.getAddress(id).getStreet());
    }

    @Test
    void updateArchiveTest() throws RepositoryException, ClientException, JsonProcessingException {
        assertTrue(clientMongoService.add("Jan", "Kowalski", "997", "123", "Lodz", "12", "Dabrowskiego", "Basic"));
        UUID id = clientMongoService.getAllClients().get(0).getEntityId();
        assertEquals(Client.class, clientMongoService.updateArchive(id, true).getClass());
        assertEquals(clientMongoService.getClient(id).isArchive(), true);
    }

    @Test
    void updateBalanceTest() throws RepositoryException, ClientException, JsonProcessingException {
        assertTrue(clientMongoService.add("Jan", "Kowalski", "997", "123", "Lodz", "12", "Dabrowskiego", "Basic"));
        UUID id = clientMongoService.getAllClients().get(0).getEntityId();
        assertEquals(Client.class, clientMongoService.updateBalance(id, 100.0).getClass());
        assertEquals(clientMongoService.getClient(id).getBalance(), 100.0);
    }

    @Test
    void updateFirstNameTest() throws RepositoryException, ClientException, JsonProcessingException {
        assertTrue(clientMongoService.add("Jan", "Kowalski", "997", "123", "Lodz", "12", "Dabrowskiego", "Basic"));
        UUID id = clientMongoService.getAllClients().get(0).getEntityId();
        assertEquals(Client.class, clientMongoService.updateFirstName(id, "Adam").getClass());
        assertEquals(clientMongoService.getClient(id).getFirstName(), "Adam");
    }

    @Test
    void updateLastNameTest() throws RepositoryException, ClientException, JsonProcessingException {
        assertTrue(clientMongoService.add("Jan", "Kowalski", "997", "123", "Lodz", "12", "Dabrowskiego", "Basic"));
        UUID id = clientMongoService.getAllClients().get(0).getEntityId();
        assertEquals(Client.class, clientMongoService.updateLastName(id, "Nowak").getClass());
        assertEquals(clientMongoService.getClient(id).getLastName(), "Nowak");
    }

    @Test
    void updatePhoneNumberTest() throws RepositoryException, ClientException, JsonProcessingException {
        assertTrue(clientMongoService.add("Jan", "Kowalski", "997", "123", "Lodz", "12", "Dabrowskiego", "Basic"));
        UUID id = clientMongoService.getAllClients().get(0).getEntityId();
        assertEquals(Client.class, clientMongoService.updatePhoneNumber(id, "112").getClass());
        assertEquals(clientMongoService.getClient(id).getPhoneNumber(), "112");
    }

    @Test
    void updateClientTypeTest() throws RepositoryException, ClientException, JsonProcessingException {
        assertTrue(clientMongoService.add("Jan", "Kowalski", "997", "123", "Lodz", "12", "Dabrowskiego", "Basic"));
        UUID id = clientMongoService.getAllClients().get(0).getEntityId();
        assertEquals(Client.class, clientMongoService.updateClientType(id, ClientTypeConverter.toRepositoryModel(new Vip())).getClass());
        assertEquals(clientMongoService.getClient(id).getClientType().getTypeName(), "Vip");
    }

    @Test
    void updateCityTest() throws RepositoryException, ClientException, JsonProcessingException {
        assertTrue(clientMongoService.add("Jan", "Kowalski", "997", "123", "Lodz", "12", "Dabrowskiego", "Basic"));
        UUID id = clientMongoService.getAllClients().get(0).getEntityId();
        assertEquals(Client.class, clientMongoService.updateCity(id, "Gdynia").getClass());
        assertEquals(clientMongoService.getClient(id).getAddress().getCity(), "Gdynia");
    }

    @Test
    void updateNumberTest() throws RepositoryException, ClientException, JsonProcessingException {
        assertTrue(clientMongoService.add("Jan", "Kowalski", "997", "123", "Lodz", "12", "Dabrowskiego", "Basic"));
        UUID id = clientMongoService.getAllClients().get(0).getEntityId();
        assertEquals(Client.class, clientMongoService.updateNumber(id, "10").getClass());
        assertEquals(clientMongoService.getClient(id).getAddress().getNumber(), "10");
    }

    @Test
    void updateStreetTest() throws RepositoryException, ClientException, JsonProcessingException {
        assertTrue(clientMongoService.add("Jan", "Kowalski", "997", "123", "Lodz", "12", "Dabrowskiego", "Basic"));
        UUID id = clientMongoService.getAllClients().get(0).getEntityId();
        assertEquals(Client.class, clientMongoService.updateStreet(id, "Kilinskiego").getClass());
        assertEquals(clientMongoService.getClient(id).getAddress().getStreet(), "Kilinskiego");
    }

    @Test
    void updateRepairsTest() throws RepositoryException, ClientException, JsonProcessingException {
        assertTrue(clientMongoService.add("Jan", "Kowalski", "997", "123", "Lodz", "12", "Dabrowskiego", "Basic"));
        UUID id = clientMongoService.getAllClients().get(0).getEntityId();
        assertEquals(Client.class, clientMongoService.updateRepairs(id).getClass());
        assertEquals(clientMongoService.getClient(id).getRepairs(), 1);
        assertEquals(Client.class, clientMongoService.updateRepairs(id).getClass());
        assertEquals(clientMongoService.getClient(id).getRepairs(), 2);
        assertThrows(RepositoryException.class,
                ()-> clientMongoService.updateRepairs(id));
    }

    @Test
    void deleteTest() throws RepositoryException, ClientException, JsonProcessingException {
        assertTrue(clientMongoService.add("Jan", "Kowalski", "997", "123", "Lodz", "12", "Dabrowskiego", "Basic"));
        UUID id1 = clientMongoService.getAllClients().get(0).getEntityId();
        assertTrue(clientMongoService.add("Jan", "Kowalski", "997", "123", "Lodz", "12", "Dabrowskiego", "Basic"));
        UUID id2 = clientMongoService.getAllClients().get(1).getEntityId();
        assertTrue(clientMongoService.delete(id1));
        assertEquals(1, clientMongoService.getAllClients().size());
        assertFalse(clientMongoService.delete(id1));
        assertTrue(clientMongoService.delete(id2));
        assertEquals(0, clientMongoService.getAllClients().size());
    }

    @AfterEach
    void clearDatabase() {
        clientMongoRepository.removeAll();
    }
}
