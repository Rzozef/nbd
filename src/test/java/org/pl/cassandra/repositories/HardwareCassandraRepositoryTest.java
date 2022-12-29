package org.pl.cassandra.repositories;

import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.truncate.Truncate;
import org.junit.jupiter.api.*;
import org.pl.cassandra.model.HardwareCassandra;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class HardwareCassandraRepositoryTest {
    private static HardwareCassandraRepository hardwareCassandraRepository;
    private HardwareCassandra hardwareCassandra;
    private final UUID hardwareUUID = UUID.randomUUID();

    @BeforeAll
    static void setUp() {
        hardwareCassandraRepository = new HardwareCassandraRepository();
    }

    @BeforeEach
    void beforeEach() {
        hardwareCassandra = new HardwareCassandra(hardwareUUID, false, 200, "computer", "FINE");
    }

    @Test
    void createHardwarePositiveTest() {
        assertTrue(hardwareCassandraRepository.create(hardwareCassandra));
    }

    @Test
    void createHardwareNegativeTest() {
        assertTrue(hardwareCassandraRepository.create(hardwareCassandra));
        assertFalse(hardwareCassandraRepository.create(hardwareCassandra));
    }

    @Test
    void findHardwareByUIdPositiveTest() {
        assertTrue(hardwareCassandraRepository.create(hardwareCassandra));
        assertEquals(hardwareUUID, hardwareCassandraRepository.findByUId(hardwareUUID).getId());
    }

    @Test
    void findHardwareByUIdNegativeTest() {
        assertNull(hardwareCassandraRepository.findByUId(hardwareUUID));
    }

    @Test
    void findAllHardwaresTest() {
        assertEquals(0, hardwareCassandraRepository.findAll().size());
        assertTrue(hardwareCassandraRepository.create(hardwareCassandra));
        assertEquals(1, hardwareCassandraRepository.findAll().size());
        HardwareCassandra hardwareCassandra1 = new HardwareCassandra(UUID.randomUUID(), false, 350, "monitor", "AVERAGE");
        assertTrue(hardwareCassandraRepository.create(hardwareCassandra1));
        assertEquals(2, hardwareCassandraRepository.findAll().size());
    }

    @Test
    void updateHardwareTest() {
        assertTrue(hardwareCassandraRepository.create(hardwareCassandra));
        assertEquals(200, hardwareCassandraRepository.findByUId(hardwareUUID).getPrice());
        hardwareCassandra.setPrice(300);
        hardwareCassandraRepository.update(hardwareCassandra);
        assertEquals(300, hardwareCassandraRepository.findByUId(hardwareUUID).getPrice());
    }

    @Test
    void deleteHardwareTest() {
        assertTrue(hardwareCassandraRepository.create(hardwareCassandra));
        assertNotNull(hardwareCassandraRepository.findByUId(hardwareUUID));
        hardwareCassandraRepository.delete(hardwareCassandra);
        assertNull(hardwareCassandraRepository.findByUId(hardwareUUID));
    }

    @Test
    void deleteAllHardwaresTest() {
        HardwareCassandra hardwareCassandra1 = new HardwareCassandra(UUID.randomUUID(), false, 350, "monitor", "AVERAGE");
        assertTrue(hardwareCassandraRepository.create(hardwareCassandra1));
        assertTrue(hardwareCassandraRepository.create(hardwareCassandra));
        assertEquals(2, hardwareCassandraRepository.findAll().size());
        hardwareCassandraRepository.deleteAll();
        assertEquals(0, hardwareCassandraRepository.findAll().size());
    }

    @AfterEach
    void afterEach() {
        hardwareCassandraRepository.deleteAll();
    }

    @AfterAll
    static void tearDown() {
        hardwareCassandraRepository.close();
    }
}
