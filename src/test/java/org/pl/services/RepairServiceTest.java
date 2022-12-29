package org.pl.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.exceptions.ClientException;
import org.pl.exceptions.HardwareException;
import org.pl.exceptions.RepairException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.*;
import org.pl.repositories.RepairRepository;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RepairServiceTest {
    private Address address1, address2;
    private Client client1, client2;
    private Hardware hardware1, hardware2;
    private HardwareType computer, monitor;
    private ArrayList<Repair> repairs;
    private RepairRepository repairRepository;
    private RepairService repairService;

    @BeforeEach
    void setUp() {
        address1 = new Address("Warszawa", "34", "Uliczna");
        address2 = new Address("Warszawa", "23", "Kolorowa");
        client1 = new Client(UUID.randomUUID(), 100, "Szymon", "Kowalski", "12345678901",
                "123456789", new Premium(), address1);
        client2 = new Client(UUID.randomUUID(), 100, "Kacper", "Jackowski", "12345678901",
                "987654321", new Vip(), address2);

        computer = new Computer(Condition.DUSTY);
        monitor = new Monitor(Condition.AVERAGE);
        hardware1 = new Hardware(UUID.randomUUID(),2000, computer, false, "");
        hardware2 = new Hardware(UUID.randomUUID(),3000, monitor, false, "");
        repairs = new ArrayList<>();
        repairRepository = new RepairRepository(repairs);
        repairService = new RepairService(repairRepository);
    }

    @Test
    void repairServiceAddPositiveTest() throws RepositoryException, RepairException {
        Repair repair = repairService.add(client1, hardware1);
        assertNotNull(repairService.get(repair.getId()));
    }

    @Test
    void repairServiceAddNegativeTest() {
        assertThrows(RepairException.class,
                ()-> repairService.add(null, hardware1));
        assertThrows(RepairException.class,
                ()-> repairService.add(client1, null));
        assertThrows(RepositoryException.class,
                ()-> repairService.get(UUID.randomUUID()));
    }

    @Test
    void repairServiceGetInfoTest() throws RepositoryException, RepairException {
        Repair repair = repairService.add(client1, hardware1);
        String expectedInfo = "Repair{id=" + repair.getId() + ", archive=false, client=Client{id=" + repair.getClient().getId() + ", archive=false, balance=100.0, firstName='Szymon', lastName='Kowalski', personalId='12345678901', phoneNumber='123456789', clientType=org.pl.model.Premium@4fa44017, address=Address{city='Warszawa', number='34', street='Uliczna'}}, hardware=Hardware{id="+ repair.getHardware().getId() + ", archive=false, price=2000, hardwareType=Computer{condition=DUSTY}}}";
        assertEquals(expectedInfo, repairService.getInfo(repair.getId()));
    }

    @Test
    void repairServiceRemovePositiveTest() throws RepositoryException, RepairException, HardwareException, ClientException {
        Repair repair1 = repairService.add(client1, hardware1);
        assertEquals(1, repairService.getPresentSize());
        Repair repair2 = repairService.add(client2, hardware2);
        assertEquals(2, repairService.getPresentSize());
        repairService.repair(repair1.getId());
        assertEquals(1, repairService.getPresentSize());
        assertTrue(repairService.get(repair1.getId()).isArchive());
        repairService.repair(repair2.getId());
        assertTrue(repairService.get(repair2.getId()).isArchive());
    }

    @Test
    void repairServiceRemoveNegativeTest() throws HardwareException, RepositoryException, ClientException, RepairException {
        Repair repair1 = repairService.add(client1, hardware1);
        Repair repair2 = repairService.add(client2, hardware2);
        repairService.repair(repair1.getId());
        assertThrows(RepositoryException.class,
                ()-> repairService.repair(repair1.getId()));
    }

    @Test
    void repairServiceGetSizeTest() throws RepositoryException, RepairException, HardwareException, ClientException {
        assertEquals(0, repairService.getPresentSize());
        assertEquals(0, repairService.getArchiveSize());
        Repair repair1 = repairService.add(client1, hardware1);
        assertEquals(1, repairService.getPresentSize());
        assertEquals(0, repairService.getArchiveSize());
        Repair repair2 = repairService.add(client2, hardware2);
        assertEquals(2, repairService.getPresentSize());
        assertEquals(0, repairService.getArchiveSize());
        repairService.repair(repair1.getId());
        assertEquals(1, repairService.getPresentSize());
        assertEquals(1, repairService.getArchiveSize());
        repairService.repair(repair2.getId());
        assertEquals(0, repairService.getPresentSize());
        assertEquals(2, repairService.getArchiveSize());
    }

    @Test
    void repairServiceRepairTest() throws RepositoryException, RepairException, HardwareException, ClientException {
        Repair repair = repairService.add(client1, hardware1);
        assertFalse(repairService.get(repair.getId()).isArchive());
        repairService.repair(repair.getId());
        assertTrue(repairService.get(repair.getId()).isArchive());
    }

    @Test
    void repairServiceRepairArchiveTest() throws RepositoryException, RepairException, HardwareException, ClientException {
        Repair repair1 = repairService.add(client1, hardware1);
        Repair repair2 = repairService.add(client1, hardware2);
        assertFalse(client1.isArchive());
        assertFalse(hardware1.isArchive());
        assertFalse(hardware2.isArchive());
        repairService.repair(repair1.getId());
        assertFalse(client1.isArchive());
        assertTrue(hardware1.isArchive());
        assertFalse(hardware2.isArchive());
        repairService.repair(repair2.getId());
        assertTrue(client1.isArchive());
        assertTrue(hardware1.isArchive());
        assertTrue(hardware2.isArchive());
    }

    @Test
    void repairServiceChangeBalanceTest() throws RepositoryException, RepairException, HardwareException, ClientException {
        Repair repair1 = repairService.add(client1, hardware1);
        Repair repair2 = repairService.add(client1, hardware2);
        assertEquals(100.0, client1.getBalance());
        repairService.repair(repair2.getId());
        assertEquals(-2060, client1.getBalance());
        repairService.repair(repair1.getId());
        assertEquals(-2064.5, client1.getBalance());
    }
}
