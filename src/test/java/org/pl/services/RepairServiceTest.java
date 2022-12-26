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
        address1 = Address.builder()
                .city("Warszawa")
                .street("Uliczna")
                .number("34")
                .build();
        address2 = Address.builder()
                .city("Warszawa")
                .street("Kolorowa")
                .number("23")
                .build();
        client1 = Client.builder()
                .id(UUID.randomUUID())
                .personalId("12345678901")
                .firstName("Szymon")
                .lastName("Kowalski")
                .phoneNumber("123456789")
                .address(address1)
                .clientType(new Premium())
                .build();
        client2 = Client.builder()
                .id(UUID.randomUUID())
                .personalId("12345678901")
                .firstName("Kacper")
                .lastName("Jackowski")
                .phoneNumber("987654321")
                .address(address2)
                .clientType(new Vip())
                .build();
        computer = new Computer(Condition.DUSTY);
        monitor = new Monitor(Condition.AVERAGE);
        hardware1 = Hardware.builder()
                .id(UUID.randomUUID())
                .price(2000)
                .hardwareType(computer)
                .build();
        hardware2 = Hardware.builder()
                .id(UUID.randomUUID())
                .price(3000)
                .hardwareType(monitor)
                .build();
        repairs = new ArrayList<>();
        repairRepository = new RepairRepository(repairs);
        repairService = new RepairService(repairRepository);
    }

    @Test
    void repairServiceAddPositiveTest() throws RepositoryException, RepairException {
        Repair repair = repairService.add(client1, hardware1);
        assertNotNull(repairService.get(repair.getID()));
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
        String expectedInfo = "Repair(id=" + repair.getID() + ", archive=false, client=Client(id=" + client1.getID() + ", archive=false, balance=0.0, firstName=Szymon, lastName=Kowalski, personalId=12345678901, phoneNumber=123456789, clientType=Premium(), address=Address(city=Warszawa, number=34, street=Uliczna)), hardware=Hardware(id=" + hardware1.getID() + ", archive=false, price=2000, hardwareType=Computer(condition=DUSTY)))";
        assertEquals(expectedInfo, repairService.getInfo(repair.getID()));
    }

    @Test
    void repairServiceRemovePositiveTest() throws RepositoryException, RepairException, HardwareException, ClientException {
        Repair repair1 = repairService.add(client1, hardware1);
        assertEquals(1, repairService.getPresentSize());
        Repair repair2 = repairService.add(client2, hardware2);
        assertEquals(2, repairService.getPresentSize());
        repairService.repair(repair1.getID());
        assertEquals(1, repairService.getPresentSize());
        assertTrue(repairService.get(repair1.getID()).isArchive());
        repairService.repair(repair2.getID());
        assertTrue(repairService.get(repair2.getID()).isArchive());
    }

    @Test
    void repairServiceRemoveNegativeTest() throws HardwareException, RepositoryException, ClientException, RepairException {
        Repair repair1 = repairService.add(client1, hardware1);
        Repair repair2 = repairService.add(client2, hardware2);
        repairService.repair(repair1.getID());
        assertThrows(RepositoryException.class,
                ()-> repairService.repair(repair1.getID()));
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
        repairService.repair(repair1.getID());
        assertEquals(1, repairService.getPresentSize());
        assertEquals(1, repairService.getArchiveSize());
        repairService.repair(repair2.getID());
        assertEquals(0, repairService.getPresentSize());
        assertEquals(2, repairService.getArchiveSize());
    }

    @Test
    void repairServiceRepairTest() throws RepositoryException, RepairException, HardwareException, ClientException {
        Repair repair = repairService.add(client1, hardware1);
        assertFalse(repairService.get(repair.getID()).isArchive());
        repairService.repair(repair.getID());
        assertTrue(repairService.get(repair.getID()).isArchive());
    }

    @Test
    void repairServiceRepairArchiveTest() throws RepositoryException, RepairException, HardwareException, ClientException {
        Repair repair1 = repairService.add(client1, hardware1);
        Repair repair2 = repairService.add(client1, hardware2);
        assertFalse(client1.isArchive());
        assertFalse(hardware1.isArchive());
        assertFalse(hardware2.isArchive());
        repairService.repair(repair1.getID());
        assertFalse(client1.isArchive());
        assertTrue(hardware1.isArchive());
        assertFalse(hardware2.isArchive());
        repairService.repair(repair2.getID());
        assertTrue(client1.isArchive());
        assertTrue(hardware1.isArchive());
        assertTrue(hardware2.isArchive());
    }

    @Test
    void repairServiceChangeBalanceTest() throws RepositoryException, RepairException, HardwareException, ClientException {
        Repair repair1 = repairService.add(client1, hardware1);
        Repair repair2 = repairService.add(client1, hardware2);
        assertEquals(0, client1.getBalance());
        repairService.repair(repair2.getID());
        assertEquals(-2160, client1.getBalance());
        repairService.repair(repair1.getID());
        assertEquals(-2164.5, client1.getBalance());
    }
}
