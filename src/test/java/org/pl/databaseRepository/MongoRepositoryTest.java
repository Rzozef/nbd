package org.pl.databaseRepository;

import org.junit.jupiter.api.Test;
import org.pl.databaseModel.*;
import org.pl.model.Condition;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MongoRepositoryTest {
    @Test
    void testConnectivity() {
        ClientMongoRepository clientMongoRepository = new ClientMongoRepository();
        ClientTypeMgd clientTypeMgd = new ClientTypeMgd((float) 1.2, 3, "basic");
        ClientAddressMgd clientAddressMgd = new ClientAddressMgd(UUID.randomUUID(), false, 200, "Jan", "Kowalski", "12345678901", "123456789", clientTypeMgd, 0, "Lodz", "7", "Zielona");
        //clientMongoRepository.initConnection();
        HardwareMongoRepository hardwareMongoRepository = new HardwareMongoRepository();
        HardwareTypeMgd hardwareTypeMgd = new ComputerMgd(Condition.AVERAGE);
        HardwareMgd hardwareMgd = new HardwareMgd(UUID.randomUUID(), false, 200, hardwareTypeMgd);
        HardwareMgd hardwareMgd2 = new HardwareMgd(UUID.randomUUID(), false, 300, hardwareTypeMgd);
        hardwareMongoRepository.initConnection();
        hardwareMongoRepository.add(hardwareMgd);
        RepairMongoRepository repairMongoRepository = new RepairMongoRepository();
        RepairEmbeddedMgd repairEmbeddedMgd = new RepairEmbeddedMgd(UUID.randomUUID(), false, clientAddressMgd, hardwareMgd);
        RepairEmbeddedMgd repairEmbeddedMgd2 = new RepairEmbeddedMgd(UUID.randomUUID(), false, clientAddressMgd, hardwareMgd2);
        //repairMongoRepository.add(repairEmbeddedMgd);
        //repairMongoRepository.add(repairEmbeddedMgd2);
        //repairMongoRepository.initConnection();
        ClientAddressMgd clientAddressMgd2 = new ClientAddressMgd(UUID.randomUUID(), false, 200, "Janek", "Kowalski", "12345678901", "123456789", clientTypeMgd, 0, "Lodz", "17", "Zielona");
        HardwareMgd hardwareMgd3 = new HardwareMgd(UUID.randomUUID(), false, 300, hardwareTypeMgd);
        //repairMongoRepository.updateHardware(0, hardwareMgd3);
        hardwareMongoRepository.add(hardwareMgd3);
        System.out.println(hardwareMongoRepository.findAllComputers());
    }
}
