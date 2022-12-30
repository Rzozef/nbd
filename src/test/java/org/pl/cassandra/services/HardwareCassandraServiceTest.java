package org.pl.cassandra.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.pl.cassandra.model.HardwareCassandra;
import org.pl.cassandra.repositories.HardwareCassandraRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class HardwareCassandraServiceTest {
    private static HardwareCassandraRepository hardwareCassandraRepository;
    private static HardwareCassandraService hardwareCassandraService;

    @BeforeAll
    static void setUp() {
        hardwareCassandraRepository = new HardwareCassandraRepository();
        hardwareCassandraService = new HardwareCassandraService(hardwareCassandraRepository);
    }

    @Test
    void createHardwareTest() {
        HardwareCassandra hardwareCassandra = hardwareCassandraService.create(200, "computer", "FINE");
        assertNotNull(hardwareCassandra);
        assertNotNull(hardwareCassandra.getId());
        assertFalse(hardwareCassandra.isArchive());
        assertEquals(200, hardwareCassandra.getPrice());
        assertEquals("computer", hardwareCassandra.getHardwareType());
        assertEquals("FINE", hardwareCassandra.getCondition());
    }

    @Test
    void findByUIdPositiveTest() {
        HardwareCassandra hardwareCassandra = hardwareCassandraService.create(200, "computer", "FINE");
        HardwareCassandra hardwareCassandra1 = hardwareCassandraService.findByUId(hardwareCassandra.getId());
        assertEquals(hardwareCassandra.getId(), hardwareCassandra1.getId());
        assertEquals(hardwareCassandra.isArchive(), hardwareCassandra1.isArchive());
        assertEquals(hardwareCassandra.getPrice(), hardwareCassandra1.getPrice());
        assertEquals(hardwareCassandra.getHardwareType(), hardwareCassandra1.getHardwareType());
        assertEquals(hardwareCassandra.getCondition(), hardwareCassandra1.getCondition());
    }

    @Test
    void findByUIdNegativeTest() {
        assertNull(hardwareCassandraService.findByUId(UUID.randomUUID()));
    }

    @Test
    void findAllTest() {
        assertEquals(0, hardwareCassandraService.findAll().size());
        hardwareCassandraService.create(200, "computer", "FINE");
        assertEquals(1, hardwareCassandraService.findAll().size());
        hardwareCassandraService.create(200, "computer", "FINE");
        assertEquals(2, hardwareCassandraService.findAll().size());
    }

    @Test
    void updateTest() {
        HardwareCassandra hardwareCassandra = hardwareCassandraService.create(200, "computer", "FINE");
        hardwareCassandra.setArchive(true);
        hardwareCassandraService.update(hardwareCassandra);
        assertEquals(hardwareCassandra.isArchive(), hardwareCassandraService.findByUId(hardwareCassandra.getId()).isArchive());
    }

    @Test
    void deleteTest() {
        HardwareCassandra hardwareCassandra = hardwareCassandraService.create(200, "computer", "FINE");
        assertNotNull(hardwareCassandraService.findByUId(hardwareCassandra.getId()));
        hardwareCassandraService.delete(hardwareCassandra.getId());
        assertNull(hardwareCassandraService.findByUId(hardwareCassandra.getId()));
    }

    @Test
    void deleteAllTest() {
        hardwareCassandraService.create(200, "computer", "FINE");
        hardwareCassandraService.create(200, "computer", "FINE");
        assertEquals(2, hardwareCassandraService.findAll().size());
        hardwareCassandraService.deleteAll();
        assertEquals(0, hardwareCassandraService.findAll().size());
    }

    @AfterEach
    void afterEach() {
        hardwareCassandraService.deleteAll();
    }
}
