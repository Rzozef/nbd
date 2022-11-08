package org.pl.databaseRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.databaseModel.*;
import org.pl.model.Condition;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class RepairMongoRepositoryTest {
    private RepairMongoRepository repairMongoRepository;
    private ComputerMgd computerMgd;
    private HardwareMgd hardwareMgd;
    private HardwareMgd hardwareMgd2;
    private ClientAddressMgd clientAddressMgd;
    private ClientAddressMgd clientAddressMgd2;
    private ClientTypeMgd clientTypeMgd;
    private RepairEmbeddedMgd repairEmbeddedMgd;
    private RepairEmbeddedMgd repairEmbeddedMgd2;
    private RepairEmbeddedMgd repairEmbeddedMgd3;
    private RepairEmbeddedMgd repairEmbeddedMgd4;

    @BeforeEach
    void setUp() {
        computerMgd = new ComputerMgd(Condition.FINE);
        hardwareMgd = new HardwareMgd(UUID.randomUUID(), false, 200, computerMgd);
        hardwareMgd2 = new HardwareMgd(UUID.randomUUID(), false, 200, computerMgd);
        clientTypeMgd = new ClientTypeMgd(1.0f, 2, "Basic");
        clientAddressMgd = new ClientAddressMgd(UUID.randomUUID(), false, 200, "Jan", "Slawko", "12345678901", "123456789", clientTypeMgd, 0, "city", "5", "street");
        clientAddressMgd2 = new ClientAddressMgd(UUID.randomUUID(), false, 200, "Jan", "Slawko", "12345678901", "123456789", clientTypeMgd, 0, "city", "5", "street");
        repairEmbeddedMgd = new RepairEmbeddedMgd(UUID.randomUUID(), false, clientAddressMgd, hardwareMgd);
        repairEmbeddedMgd2 = new RepairEmbeddedMgd(UUID.randomUUID(), false, clientAddressMgd2, hardwareMgd);
        repairEmbeddedMgd3 = new RepairEmbeddedMgd(UUID.randomUUID(), false, clientAddressMgd, hardwareMgd2);
        repairEmbeddedMgd4 = new RepairEmbeddedMgd(UUID.randomUUID(), false, clientAddressMgd2, hardwareMgd2);
        repairMongoRepository = new RepairMongoRepository();
        repairMongoRepository.initConnection();
    }

    @Test
    void addRepairPositiveTest() {
        assertTrue(repairMongoRepository.add(repairEmbeddedMgd));
    }

    @Test
    void addRepairNegativeTest() {
        assertTrue(repairMongoRepository.add(repairEmbeddedMgd));
        assertFalse(repairMongoRepository.add(repairEmbeddedMgd));
    }

    @Test
    void findAllTest() {
        repairMongoRepository.add(repairEmbeddedMgd);
        repairMongoRepository.add(repairEmbeddedMgd2);
        assertEquals(2, repairMongoRepository.findAll().size());
    }

    @Test
    void findAllRepairsByClientId() {
        repairMongoRepository.add(repairEmbeddedMgd);
        repairMongoRepository.add(repairEmbeddedMgd2);
        repairMongoRepository.add(repairEmbeddedMgd3);
        repairMongoRepository.add(repairEmbeddedMgd4);
        assertEquals(2, repairMongoRepository.findAllRepairsByClientId(clientAddressMgd.getEntityId()).size());
    }

    @Test
    void findRepairPositiveTest() {
        repairMongoRepository.add(repairEmbeddedMgd);
        assertEquals(repairEmbeddedMgd.getEntityId(), repairMongoRepository.find(repairEmbeddedMgd.getEntityId()).getEntityId());
    }

    @Test
    void findRepairNegativeTest() {
        assertNull(repairMongoRepository.find(repairEmbeddedMgd.getEntityId()));
    }

    @Test
    void removeRepairTest() {
        repairMongoRepository.add(repairEmbeddedMgd);
        assertEquals(1, repairMongoRepository.findAll().size());
        repairMongoRepository.remove(repairEmbeddedMgd.getEntityId());
        assertEquals(0, repairMongoRepository.findAll().size());
    }

    @Test
    void removeAllTest() {
        repairMongoRepository.add(repairEmbeddedMgd);
        repairMongoRepository.add(repairEmbeddedMgd2);
        repairMongoRepository.add(repairEmbeddedMgd3);
        repairMongoRepository.add(repairEmbeddedMgd4);
        assertEquals(4, repairMongoRepository.findAll().size());
        repairMongoRepository.removeAll();
        assertEquals(0, repairMongoRepository.findAll().size());
    }

    @Test
    void updateArchiveTest() {
        repairMongoRepository.add(repairEmbeddedMgd);
        assertFalse(repairMongoRepository.find(repairEmbeddedMgd.getEntityId()).isArchive());
        repairMongoRepository.updateArchive(repairEmbeddedMgd.getEntityId(), true);
        assertTrue(repairMongoRepository.find(repairEmbeddedMgd.getEntityId()).isArchive());
    }

    @AfterEach
    void clearDatabase() {
        repairMongoRepository.removeAll();
    }
}
