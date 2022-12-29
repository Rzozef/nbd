package org.pl.cassandra.repositories;

import org.junit.jupiter.api.*;
import org.pl.cassandra.model.ClientCassandra;
import org.pl.cassandra.model.HardwareCassandra;
import org.pl.cassandra.model.RepairCassandra;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class RepairCassandraRepositoryTest {
    private static RepairCassandraRepository repairCassandraRepository;
    private ClientCassandra clientCassandra;
    private HardwareCassandra hardwareCassandra;
    private RepairCassandra repairCassandra;
    private final UUID clientUUID = UUID.randomUUID();
    private final UUID hardwareUUID = UUID.randomUUID();
    private final UUID repairUUID = UUID.randomUUID();

    @BeforeAll
    static void setUp() {
        repairCassandraRepository = new RepairCassandraRepository();
    }

    @BeforeEach
    void beforeEach() {
        clientCassandra = new ClientCassandra(clientUUID, false, 300, "Jan", "Kowalski", "12345678901", "123456789", (float)1.0, 3, "basic", "Warsaw", "Zielona", "77");
        hardwareCassandra = new HardwareCassandra(hardwareUUID, false, 200, "computer", "FINE");
        repairCassandra = new RepairCassandra(repairUUID, false, clientUUID, hardwareUUID);
    }

    @Test
    void createRepairPositiveTest() {
        assertTrue(repairCassandraRepository.create(repairCassandra));
    }

    @Test
    void createRepairNegativeTest() {
        assertTrue(repairCassandraRepository.create(repairCassandra));
        assertFalse(repairCassandraRepository.create(repairCassandra));
    }

    @Test
    void findRepairByUIdPositiveTest() {
        assertTrue(repairCassandraRepository.create(repairCassandra));
        assertEquals(repairCassandraRepository.findByUId(repairUUID).getClient(), clientUUID);
    }

    @Test
    void findRepairByUIdNegativeTest() {
        assertNull(repairCassandraRepository.findByUId(repairUUID));
    }

    @Test
    void findAllRepairsTest() {
        assertEquals(0, repairCassandraRepository.findAll().size());
        assertTrue(repairCassandraRepository.create(repairCassandra));
        assertEquals(1, repairCassandraRepository.findAll().size());
        ClientCassandra clientCassandra1 = new ClientCassandra(UUID.randomUUID(), false, 300, "Jan", "Kowalski", "12345678901", "123456789", (float)1.0, 3, "basic", "Warsaw", "Zielona", "77");
        HardwareCassandra hardwareCassandra1 = new HardwareCassandra(UUID.randomUUID(), false, 200, "computer", "FINE");
        RepairCassandra repairCassandra1 = new RepairCassandra(UUID.randomUUID(), false, clientCassandra1.getId(), hardwareCassandra1.getId());
        assertTrue(repairCassandraRepository.create(repairCassandra1));
        assertEquals(2, repairCassandraRepository.findAll().size());
    }

    @Test
    void updateRepairTest() {
        assertTrue(repairCassandraRepository.create(repairCassandra));
        assertFalse(repairCassandraRepository.findByUId(repairUUID).isArchive());
        repairCassandra.setArchive(true);
        repairCassandraRepository.update(repairCassandra);
        assertTrue(repairCassandraRepository.findByUId(repairUUID).isArchive());
    }

    @Test
    void deleteRepairTest() {
        assertTrue(repairCassandraRepository.create(repairCassandra));
        assertNotNull(repairCassandraRepository.findByUId(repairUUID));
        repairCassandraRepository.delete(repairCassandra);
        assertNull(repairCassandraRepository.findByUId(repairUUID));
    }

    @Test
    void deleteAllRepairsTest() {
        ClientCassandra clientCassandra1 = new ClientCassandra(UUID.randomUUID(), false, 300, "Jan", "Kowalski", "12345678901", "123456789", (float)1.0, 3, "basic", "Warsaw", "Zielona", "77");
        HardwareCassandra hardwareCassandra1 = new HardwareCassandra(UUID.randomUUID(), false, 200, "computer", "FINE");
        RepairCassandra repairCassandra1 = new RepairCassandra(UUID.randomUUID(), false, clientCassandra1.getId(), hardwareCassandra1.getId());
        assertTrue(repairCassandraRepository.create(repairCassandra1));
        assertTrue(repairCassandraRepository.create(repairCassandra));
        assertEquals(2, repairCassandraRepository.findAll().size());
        repairCassandraRepository.deleteAll();
        assertEquals(0, repairCassandraRepository.findAll().size());
    }

    @AfterEach
    void afterEach() {
        repairCassandraRepository.deleteAll();
    }

    @AfterAll
    static void tearDown() {
        repairCassandraRepository.close();
    }
}
