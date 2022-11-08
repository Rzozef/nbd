package org.pl.databaseRepository;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.databaseModel.*;
import org.pl.model.Condition;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class HardwareMongoRepositoryTest {
    private HardwareMongoRepository hardwareMongoRepository;
    private ComputerMgd computerMgd;
    private ConsoleMgd consoleMgd;
    private PhoneMgd phoneMgd;
    private MonitorMgd monitorMgd;
    private HardwareMgd hardwareMgd;
    private HardwareMgd hardwareMgd2;
    private HardwareMgd hardwareMgd3;
    private HardwareMgd hardwareMgd4;

    @BeforeEach
    void setUp() {
        computerMgd = new ComputerMgd(Condition.FINE);
        consoleMgd = new ConsoleMgd(Condition.FINE);
        phoneMgd = new PhoneMgd(Condition.FINE);
        monitorMgd = new MonitorMgd(Condition.FINE);
        hardwareMgd = new HardwareMgd(UUID.randomUUID(), false, 200, computerMgd);
        hardwareMgd2 = new HardwareMgd(UUID.randomUUID(), false, 300, consoleMgd);
        hardwareMgd3 = new HardwareMgd(UUID.randomUUID(), false, 400, phoneMgd);
        hardwareMgd4 = new HardwareMgd(UUID.randomUUID(), false, 500, monitorMgd);
        hardwareMongoRepository = new HardwareMongoRepository();
        hardwareMongoRepository.initConnection();
    }

    @Test
    void addHardwarePositiveTest() {
        assertTrue(hardwareMongoRepository.add(hardwareMgd));
    }

    @Test
    void addHardwareNegativeTest() {
        assertTrue(hardwareMongoRepository.add(hardwareMgd));
        assertFalse(hardwareMongoRepository.add(hardwareMgd));
    }

    @Test
    void findAllTest() {
        hardwareMongoRepository.add(hardwareMgd);
        hardwareMongoRepository.add(hardwareMgd2);
        assertEquals(2, hardwareMongoRepository.findAll().size());
    }

    @Test
    void findAllComputersTest() {
        hardwareMongoRepository.add(hardwareMgd);
        hardwareMongoRepository.add(hardwareMgd2);
        hardwareMongoRepository.add(hardwareMgd3);
        hardwareMongoRepository.add(hardwareMgd4);
        assertEquals(1, hardwareMongoRepository.findAllComputers().size());
        assertEquals(ComputerMgd.class, hardwareMongoRepository.findAllComputers().get(0).getClass());
    }

    @Test
    void findAllConsolesTest() {
        hardwareMongoRepository.add(hardwareMgd);
        hardwareMongoRepository.add(hardwareMgd2);
        hardwareMongoRepository.add(hardwareMgd3);
        hardwareMongoRepository.add(hardwareMgd4);
        assertEquals(1, hardwareMongoRepository.findAllConsoles().size());
        assertEquals(ConsoleMgd.class, hardwareMongoRepository.findAllConsoles().get(0).getClass());
    }

    @Test
    void findAllPhonesTest() {
        hardwareMongoRepository.add(hardwareMgd);
        hardwareMongoRepository.add(hardwareMgd2);
        hardwareMongoRepository.add(hardwareMgd3);
        hardwareMongoRepository.add(hardwareMgd4);
        assertEquals(1, hardwareMongoRepository.findAllPhones().size());
        assertEquals(PhoneMgd.class, hardwareMongoRepository.findAllPhones().get(0).getClass());
    }

    @Test
    void findAllMonitorsTest() {
        hardwareMongoRepository.add(hardwareMgd);
        hardwareMongoRepository.add(hardwareMgd2);
        hardwareMongoRepository.add(hardwareMgd3);
        hardwareMongoRepository.add(hardwareMgd4);
        assertEquals(1, hardwareMongoRepository.findAllMonitors().size());
        assertEquals(MonitorMgd.class, hardwareMongoRepository.findAllMonitors().get(0).getClass());
    }

    @Test
    void findHardwareByIdHardwareIsInDatabaseTest() {
        hardwareMongoRepository.add(hardwareMgd);
        assertEquals(hardwareMgd.getEntityId(), hardwareMongoRepository.find(hardwareMgd.getEntityId()).getEntityId());
    }

    @Test
    void findHardwareByIdHardwareIsNotInDatabaseTest() {
        assertNull(hardwareMongoRepository.find(hardwareMgd.getEntityId()));
    }

    @Test
    void removeHardwareByHardwareIdTest() {
        hardwareMongoRepository.add(hardwareMgd);
        assertEquals(1, hardwareMongoRepository.findAll().size());
        hardwareMongoRepository.remove(hardwareMgd.getEntityId());
        assertEquals(0, hardwareMongoRepository.findAll().size());
    }

    @Test
    void removeAllHardwaresTest() {
        hardwareMongoRepository.add(hardwareMgd);
        hardwareMongoRepository.add(hardwareMgd2);
        hardwareMongoRepository.add(hardwareMgd3);
        hardwareMongoRepository.add(hardwareMgd4);
        assertEquals(4, hardwareMongoRepository.findAll().size());
        hardwareMongoRepository.removeAll();
        assertEquals(0, hardwareMongoRepository.findAll().size());
    }

    @Test
    void updateArchiveTest() {
        hardwareMongoRepository.add(hardwareMgd);
        assertFalse(hardwareMongoRepository.find(hardwareMgd.getEntityId()).isArchive());
        hardwareMongoRepository.updateArchive(hardwareMgd.getEntityId(), true);
        assertTrue(hardwareMongoRepository.find(hardwareMgd.getEntityId()).isArchive());
    }

    @Test
    void updatePriceTest() {
        hardwareMongoRepository.add(hardwareMgd);
        assertEquals(200, hardwareMongoRepository.find(hardwareMgd.getEntityId()).getPrice());
        hardwareMongoRepository.updatePrice(hardwareMgd.getEntityId(), 100);
        assertEquals(100, hardwareMongoRepository.find(hardwareMgd.getEntityId()).getPrice());
    }

    @AfterEach
    void clearDatabase() {
        hardwareMongoRepository.removeAll();
    }
}
