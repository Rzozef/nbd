package org.pl.databaseRepository;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.databaseModel.ComputerMgd;
import org.pl.databaseModel.HardwareMgd;
import org.pl.databaseModel.HardwareTypeMgd;
import org.pl.model.Condition;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class HardwareMongoRepositoryTest {
    private HardwareMongoRepository hardwareMongoRepository;
    private HardwareTypeMgd computerMgd;
    private HardwareMgd hardwareMgd;

    @BeforeEach
    void setUp() {
        computerMgd = new ComputerMgd(Condition.FINE);
        hardwareMgd = new HardwareMgd(UUID.randomUUID(), false, 200, computerMgd);
        hardwareMongoRepository = new HardwareMongoRepository();
        hardwareMongoRepository.initConnection();
    }

    @Test
    void addHardwarePositiveTest() {
        assertTrue(hardwareMongoRepository.add(hardwareMgd));
    }

    @AfterEach
    void clearDatabase() {
        hardwareMongoRepository.removeAll();
    }
}
