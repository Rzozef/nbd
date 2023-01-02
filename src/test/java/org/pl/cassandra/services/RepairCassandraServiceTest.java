package org.pl.cassandra.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.pl.cassandra.model.RepairCassandra;
import org.pl.cassandra.repositories.RepairCassandraRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class RepairCassandraServiceTest {
    private static RepairCassandraRepository repairCassandraRepository;
    private static RepairCassandraService repairCassandraService;
    private static UUID clientUUID;
    private static UUID hardwareUUID;

    @BeforeAll
    static void setUp() {
        repairCassandraRepository = new RepairCassandraRepository();
        repairCassandraService = new RepairCassandraService(repairCassandraRepository);
        clientUUID = UUID.randomUUID();
        hardwareUUID = UUID.randomUUID();
    }

    @Test
    void createRepairTest() {
        RepairCassandra repairCassandra = repairCassandraService.create(clientUUID, hardwareUUID);
        assertNotNull(repairCassandra);
        assertNotNull(repairCassandra.getId());
        assertFalse(repairCassandra.isArchive());
        assertEquals(clientUUID, repairCassandra.getClient());
        assertEquals(hardwareUUID, repairCassandra.getHardware());
    }

    @Test
    void findByUIdTest() {
        RepairCassandra repairCassandra = repairCassandraService.create(clientUUID, hardwareUUID);
        RepairCassandra repairCassandra1 = repairCassandraService.findByUId(repairCassandra.getId());
        assertEquals(repairCassandra.getId(), repairCassandra1.getId());
        assertEquals(repairCassandra.isArchive(), repairCassandra1.isArchive());
        assertEquals(repairCassandra.getClient(), repairCassandra1.getClient());
        assertEquals(repairCassandra.getHardware(), repairCassandra1.getHardware());
    }

    @Test
    void findByUIdNegativeTest() {
        assertNull(repairCassandraService.findByUId(UUID.randomUUID()));
    }

    @Test
    void findAllTest() {
        assertEquals(0, repairCassandraService.findAll().size());
        repairCassandraService.create(clientUUID, hardwareUUID);
        assertEquals(1, repairCassandraService.findAll().size());
        repairCassandraService.create(clientUUID, hardwareUUID);
        assertEquals(2, repairCassandraService.findAll().size());
    }

    @Test
    void findByClientTest() {
        assertEquals(0, repairCassandraService.findByClient(clientUUID).size());
        repairCassandraService.create(clientUUID, hardwareUUID);
        assertEquals(1, repairCassandraService.findByClient(clientUUID).size());
        repairCassandraService.create(clientUUID, hardwareUUID);
        assertEquals(2, repairCassandraService.findByClient(clientUUID).size());
    }

    @Test
    void findByHardwareTest() {
        assertEquals(0, repairCassandraService.findByHardware(hardwareUUID).size());
        repairCassandraService.create(clientUUID, hardwareUUID);
        assertEquals(1, repairCassandraService.findByHardware(hardwareUUID).size());
        repairCassandraService.create(clientUUID, hardwareUUID);
        assertEquals(2, repairCassandraService.findByHardware(hardwareUUID).size());
    }

    @Test
    void updateTest() {
        RepairCassandra repairCassandra = repairCassandraService.create(clientUUID, hardwareUUID);
        repairCassandra.setArchive(true);
        repairCassandraService.update(repairCassandra);
        assertEquals(repairCassandra.isArchive(), repairCassandraService.findByUId(repairCassandra.getId()).isArchive());
    }

    @Test
    void deleteTest() {
        RepairCassandra repairCassandra = repairCassandraService.create(clientUUID, hardwareUUID);
        assertNotNull(repairCassandraService.findByUId(repairCassandra.getId()));
        repairCassandraService.delete(repairCassandra.getId());
        assertNull(repairCassandraService.findByUId(repairCassandra.getId()));
    }

    @Test
    void deleteAllTest() {
        repairCassandraService.create(clientUUID, hardwareUUID);
        repairCassandraService.create(clientUUID, hardwareUUID);
        assertEquals(2, repairCassandraService.findAll().size());
        repairCassandraService.deleteAll();
        assertEquals(0, repairCassandraService.findAll().size());
    }

    @AfterEach
    void afterEach() {
        repairCassandraService.deleteAll();
    }
}
