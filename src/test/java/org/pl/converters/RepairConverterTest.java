package org.pl.converters;

import org.junit.jupiter.api.Test;
import org.pl.databaseModel.*;
import org.pl.exceptions.ClientException;
import org.pl.exceptions.HardwareException;
import org.pl.model.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class RepairConverterTest {
    @Test
    void convertRepairToRepairEmbeddedTest() throws HardwareException {
        Address address = new Address("city", "5", "street");
        ClientType clientType = new Basic();
        Client client = new Client(UUID.randomUUID(), false, 200, "Jans", "Slowko", "12345678901", "123456789", clientType, address, 0);
        HardwareType computer = new Computer(Condition.FINE);
        Hardware hardware = new Hardware(UUID.randomUUID(), false, 100, computer);
        Repair repair = new Repair(UUID.randomUUID(), false, client, hardware);
        assertEquals(RepairConverter.toRepositoryModel(repair).getClass(), RepairEmbeddedMgd.class);
    }

    @Test
    void convertRepairEmbeddedMgdToRepairTest() throws HardwareException, ClientException {
        ClientTypeMgd clientTypeMgd = new ClientTypeMgd(1.0f, 2, "Basic");
        ClientAddressMgd clientAddressMgd = new ClientAddressMgd(UUID.randomUUID(), false, 200, "Jan", "Slawko", "12345678901", "123456789", clientTypeMgd, 0, "city", "5", "street");
        HardwareTypeMgd computerMgd = new ComputerMgd(Condition.FINE);
        HardwareMgd hardwareMgd = new HardwareMgd(UUID.randomUUID(), false, 100, computerMgd);
        RepairEmbeddedMgd repairEmbeddedMgd = new RepairEmbeddedMgd(UUID.randomUUID(), false, clientAddressMgd, hardwareMgd);
        assertEquals(RepairConverter.fromRepositoryModel(repairEmbeddedMgd).getClass(), Repair.class);
    }
}
