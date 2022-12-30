package org.pl.cassandra.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.cassandra.model.ClientCassandra;
import org.pl.cassandra.repositories.ClientCassandraRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ClientCassandraServiceTest {
    private static ClientCassandraRepository clientCassandraRepository;
    private static ClientCassandraService clientCassandraService;

    @BeforeAll
    static void setUp() {
        clientCassandraRepository = new ClientCassandraRepository();
        clientCassandraService = new ClientCassandraService(clientCassandraRepository);
    }

    @Test
    void createClientTest() {
        ClientCassandra clientCassandra1 = clientCassandraService.create(300, "Jan", "Kowalski", "12345678901", "123456789", (float)1.0, 3, "Basic", "Warsaw", "Zielona", "77");
        assertNotNull(clientCassandra1);
        assertNotNull(clientCassandra1.getId());
        assertFalse(clientCassandra1.isArchive());
        assertEquals(300, clientCassandra1.getBalance());
        assertEquals(clientCassandra1.getFirstName(), "Jan");
        assertEquals(clientCassandra1.getLastName(), "Kowalski");
        assertEquals(clientCassandra1.getPersonalId(), "12345678901");
        assertEquals(clientCassandra1.getPhoneNumber(), "123456789");
        assertEquals(clientCassandra1.getFactor(), (float)1.0);
        assertEquals(clientCassandra1.getMaxRepairs(), 3);
        assertEquals(clientCassandra1.getTypeName(), "Basic");
        assertEquals(clientCassandra1.getCity(), "Warsaw");
        assertEquals(clientCassandra1.getStreet(), "Zielona");
        assertEquals(clientCassandra1.getNumber(), "77");
    }

    @Test
    void findByUIdPositiveTest() {
        ClientCassandra clientCassandra1 = clientCassandraService.create(300, "Jan", "Kowalski", "12345678901", "123456789", (float)1.0, 3, "basic", "Warsaw", "Zielona", "77");
        ClientCassandra clientCassandra2 = clientCassandraService.findByUId(clientCassandra1.getId());
        assertEquals(clientCassandra1.getId(), clientCassandra2.getId());
        assertEquals(clientCassandra1.isArchive(), clientCassandra2.isArchive());
        assertEquals(clientCassandra1.getBalance(), clientCassandra2.getBalance());
        assertEquals(clientCassandra1.getFirstName(), clientCassandra2.getFirstName());
        assertEquals(clientCassandra1.getLastName(), clientCassandra2.getLastName());
        assertEquals(clientCassandra1.getPersonalId(), clientCassandra2.getPersonalId());
        assertEquals(clientCassandra1.getPhoneNumber(), clientCassandra2.getPhoneNumber());
        assertEquals(clientCassandra1.getFactor(), clientCassandra2.getFactor());
        assertEquals(clientCassandra1.getMaxRepairs(), clientCassandra2.getMaxRepairs());
        assertEquals(clientCassandra1.getTypeName(), clientCassandra2.getTypeName());
        assertEquals(clientCassandra1.getCity(), clientCassandra2.getCity());
        assertEquals(clientCassandra1.getStreet(), clientCassandra2.getStreet());
        assertEquals(clientCassandra1.getNumber(), clientCassandra2.getNumber());
    }

    @Test
    void findByUIdNegativeTest() {
        assertNull(clientCassandraService.findByUId(UUID.randomUUID()));
    }

    @Test
    void findAllTest() {
        assertEquals(0, clientCassandraService.findAll().size());
        clientCassandraService.create(300, "Jan", "Kowalski", "12345678901", "123456789", (float)1.0, 3, "basic", "Warsaw", "Zielona", "77");
        assertEquals(1, clientCassandraService.findAll().size());
        clientCassandraService.create(300, "Jan", "Kowalski", "12345678901", "123456789", (float)1.0, 3, "basic", "Warsaw", "Zielona", "77");
        assertEquals(2, clientCassandraService.findAll().size());
    }

    @Test
    void updateTest() {
        ClientCassandra clientCassandra1 = clientCassandraService.create(300, "Jan", "Kowalski", "12345678901", "123456789", (float)1.0, 3, "basic", "Warsaw", "Zielona", "77");
        clientCassandra1.setArchive(true);
        clientCassandraService.update(clientCassandra1);
        assertEquals(clientCassandra1.isArchive(), clientCassandraService.findByUId(clientCassandra1.getId()).isArchive());
    }

    @Test
    void deleteTest() {
        ClientCassandra clientCassandra1 = clientCassandraService.create(300, "Jan", "Kowalski", "12345678901", "123456789", (float)1.0, 3, "basic", "Warsaw", "Zielona", "77");
        assertNotNull(clientCassandraService.findByUId(clientCassandra1.getId()));
        clientCassandraService.delete(clientCassandra1.getId());
        assertNull(clientCassandraService.findByUId(clientCassandra1.getId()));
    }

    @Test
    void deleteAllTest() {
        clientCassandraService.create(300, "Jan", "Kowalski", "12345678901", "123456789", (float)1.0, 3, "basic", "Warsaw", "Zielona", "77");
        clientCassandraService.create(300, "Jan", "Kowalski", "12345678901", "123456789", (float)1.0, 3, "basic", "Warsaw", "Zielona", "77");
        assertEquals(2, clientCassandraService.findAll().size());
        clientCassandraService.deleteAll();
        assertEquals(0, clientCassandraService.findAll().size());
    }

    @AfterEach
    void afterEach() {
        clientCassandraService.deleteAll();
    }
}
