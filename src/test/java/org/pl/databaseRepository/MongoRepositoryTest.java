package org.pl.databaseRepository;

import org.junit.jupiter.api.Test;
import org.pl.databaseModel.*;
import org.pl.model.Condition;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MongoRepositoryTest {
    @Test
    void testConnectivity() {
        ClientMongoRepository clientMongoRepository = new ClientMongoRepository();
        ClientTypeMgd clientTypeMgd = new ClientTypeMgd((float) 1.2, 3, "basic");
        ClientAddressMgd clientAddressMgd = new ClientAddressMgd(0, false, 200, "Jan", "Kowalski", "12345678901", "123456789", clientTypeMgd, "Lodz", "7", "Zielona");
        //clientMongoRepository.initConnection();
        HardwareMongoRepository hardwareMongoRepository = new HardwareMongoRepository();
        HardwareTypeMgd hardwareTypeMgd = new ComputerMgd(Condition.AVERAGE);
        HardwareMgd hardwareMgd = new HardwareMgd(0, false, 200, hardwareTypeMgd);
        HardwareMgd hardwareMgd2 = new HardwareMgd(1, false, 300, hardwareTypeMgd);
        hardwareMongoRepository.initConnection();
        hardwareMongoRepository.add(hardwareMgd);
        RepairMongoRepository repairMongoRepository = new RepairMongoRepository();
        RepairEmbeddedMgd repairEmbeddedMgd = new RepairEmbeddedMgd(0, false, clientAddressMgd, hardwareMgd);
        RepairEmbeddedMgd repairEmbeddedMgd2 = new RepairEmbeddedMgd(1, false, clientAddressMgd, hardwareMgd2);
        //repairMongoRepository.add(repairEmbeddedMgd);
        //repairMongoRepository.add(repairEmbeddedMgd2);
        //repairMongoRepository.initConnection();
        ClientAddressMgd clientAddressMgd2 = new ClientAddressMgd(1, false, 200, "Janek", "Kowalski", "12345678901", "123456789", clientTypeMgd, "Lodz", "17", "Zielona");
        HardwareMgd hardwareMgd3 = new HardwareMgd(2, false, 300, hardwareTypeMgd);
        //repairMongoRepository.updateHardware(0, hardwareMgd3);
        hardwareMongoRepository.add(hardwareMgd3);
        System.out.println(hardwareMongoRepository.findAllComputers());
    }
}
