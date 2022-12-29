package org.pl.cassandra.repositories;

import org.junit.jupiter.api.*;
import org.pl.cassandra.model.ClientCassandra;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ClientCassandraRepositoryTest {
    private static ClientCassandraRepository clientCassandraRepository;
    private ClientCassandra clientCassandra;
    private final UUID clientUUID = UUID.randomUUID();

    @BeforeAll
    static void setUp() {
        clientCassandraRepository = new ClientCassandraRepository();
    }

    @BeforeEach
    void beforaEach() {
        clientCassandra = new ClientCassandra(clientUUID, false, 300, "Jan", "Kowalski", "12345678901", "123456789", (float)1.0, 3, "basic", "Warsaw", "Zielona", "77");
    }

    @Test
    void createClientPositiveTest() {
        assertTrue(clientCassandraRepository.create(clientCassandra));
    }

    @Test
    void createClientNegativeTest() {
        assertTrue(clientCassandraRepository.create(clientCassandra));
        assertFalse(clientCassandraRepository.create(clientCassandra));
    }

    @Test
    void findClientByUIdPositiveTest() {
        assertTrue(clientCassandraRepository.create(clientCassandra));
        assertEquals(clientUUID, clientCassandraRepository.findByUId(clientUUID).getId());
    }

    @Test
    void findClientByUIdNegativeTest() {
        assertNull(clientCassandraRepository.findByUId(clientUUID));
    }

    @Test
    void findAllClientsTest() {
        assertEquals(0, clientCassandraRepository.findAll().size());
        assertTrue(clientCassandraRepository.create(clientCassandra));
        assertEquals(1, clientCassandraRepository.findAll().size());
        ClientCassandra clientCassandra1 = new ClientCassandra(UUID.randomUUID(), false, 300, "Jan", "Kowalski", "12345678901", "123456789", (float)1.0, 3, "basic", "Warsaw", "Zielona", "77");
        assertTrue(clientCassandraRepository.create(clientCassandra1));
        assertEquals(2, clientCassandraRepository.findAll().size());
    }

    @Test
    void updateClientTest() {
        assertTrue(clientCassandraRepository.create(clientCassandra));
        assertEquals(300, clientCassandraRepository.findByUId(clientUUID).getBalance());
        clientCassandra.setBalance(500);
        clientCassandraRepository.update(clientCassandra);
        assertEquals(500, clientCassandraRepository.findByUId(clientUUID).getBalance());
    }

    @Test
    void deleteClientTest() {
        assertTrue(clientCassandraRepository.create(clientCassandra));
        assertNotNull(clientCassandraRepository.findByUId(clientUUID));
        clientCassandraRepository.delete(clientCassandra);
        assertNull(clientCassandraRepository.findByUId(clientUUID));
    }

    @Test
    void deleteAllClientsTest() {
        ClientCassandra clientCassandra1 = new ClientCassandra(UUID.randomUUID(), false, 300, "Jan", "Kowalski", "12345678901", "123456789", (float)1.0, 3, "basic", "Warsaw", "Zielona", "77");
        assertTrue(clientCassandraRepository.create(clientCassandra1));
        assertTrue(clientCassandraRepository.create(clientCassandra));
        assertEquals(2, clientCassandraRepository.findAll().size());
        clientCassandraRepository.deleteAll();
        assertEquals(0, clientCassandraRepository.findAll().size());
    }

    @AfterEach
    void afterEach() {
        clientCassandraRepository.deleteAll();
    }

    @AfterAll
    static void tearDown() {
        clientCassandraRepository.close();
    }
}
