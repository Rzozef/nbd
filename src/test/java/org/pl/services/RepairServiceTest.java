//package org.pl.services;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.pl.exceptions.ClientException;
//import org.pl.exceptions.HardwareException;
//import org.pl.exceptions.RepairException;
//import org.pl.exceptions.RepositoryException;
//import org.pl.model.*;
//import org.pl.repositories.RepairRepository;
//
//import java.util.ArrayList;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class RepairServiceTest {
//    Address address1, address2;
//    Client client1, client2;
//    Hardware hardware1, hardware2;
//    HardwareType computer, monitor;
//    ArrayList<Repair> repairs;
//    RepairRepository repairRepository;
//    RepairService repairService;
//
//    @BeforeEach
//    void setUp() {
//        address1 = Address.builder()
//                .city("Warszawa")
//                .street("Uliczna")
//                .number("34")
//                .build();
//        address2 = Address.builder()
//                .city("Warszawa")
//                .street("Kolorowa")
//                .number("23")
//                .build();
//        client1 = Client.builder()
//                .personalId("1")
//                .firstName("Szymon")
//                .lastName("Kowalski")
//                .phoneNumber("123456789")
//                .address(address1)
//                .clientType(new Premium())
//                .build();
//        client2 = Client.builder()
//                .personalId("2")
//                .firstName("Kacper")
//                .lastName("Jackowski")
//                .phoneNumber("987654321")
//                .address(address2)
//                .clientType(new Vip())
//                .build();
//        computer = new Computer(Condition.DUSTY);
//        monitor = new Monitor(Condition.AVERAGE);
//        hardware1 = Hardware.builder()
//                .entityId(UUID.randomUUID())
//                .price(2000)
//                .hardwareType(computer)
//                .build();
//        hardware2 = Hardware.builder()
//                .entityId(UUID.randomUUID())
//                .price(3000)
//                .hardwareType(monitor)
//                .build();
//        repairs = new ArrayList<>();
//        repairRepository = new RepairRepository(repairs);
//        repairService = new RepairService(repairRepository);
//    }
//
//    @Test
//    void repairServiceAddPositiveTest() throws RepositoryException, RepairException {
//        Repair repairTest1 = repairService.add(client1, hardware1);
//        assertNotNull(repairService.get(repairTest1.getID()));
//        Repair repairTest2 = repairService.add(client2, hardware2);
//        assertNotNull(repairService.get(repairTest2.getID()));
//    }
//
//    @Test
//    void repairServiceAddNegativeTest() {
//        assertThrows(RepairException.class,
//                ()-> repairService.add(null, hardware1));
//        assertThrows(RepairException.class,
//                ()-> repairService.add(client1, null));
//        assertThrows(RepositoryException.class,
//                ()-> repairService.get(null));
//    }
//
//    @Test
//    void repairServiceGetInfoTest() throws RepositoryException, RepairException {
//        Repair repairTest1 = repairService.add(client1, hardware1);
//        String expectedInfo = "Repair(archive=false, client=Client(archive=false, balance=0.0, firstName=Szymon, lastName=Kowalski, personalId=1, phoneNumber=123456789, clientType=Premium(), address=Address(city=Warszawa, number=34, street=Uliczna), repairs=0), hardware=Hardware(archive=false, price=2000, hardwareType=Computer(condition=Condition.DUSTY)))";
//        assertEquals(expectedInfo, repairService.getInfo(repairTest1.getID()));
//    }
//
//    @Test
//    void repairServiceRemovePositiveTest() throws RepositoryException, RepairException, HardwareException, ClientException {
//        Repair repairTest1 = repairService.add(client1, hardware1);
//        assertEquals(1, repairService.getPresentSize());
//        Repair repairTest2 = repairService.add(client2, hardware2);
//        assertEquals(2, repairService.getPresentSize());
//        repairService.repair(repairTest1.getID());
//        assertEquals(1, repairService.getPresentSize());
//        assertTrue(repairService.get(repairTest1.getID()).isArchive());
//        repairService.repair(repairTest2.getID());
//        assertTrue(repairService.get(repairTest2.getID()).isArchive());
//    }
//
//    @Test
//    void repairServiceRemoveNegativeTest() throws HardwareException, RepositoryException, ClientException, RepairException {
//        Repair repairTest1 = repairService.add(client1, hardware1);
//        Repair repairTest2 = repairService.add(client2, hardware2);
//        repairService.repair(repairTest1.getID());
//        assertThrows(RepositoryException.class,
//                ()-> repairService.repair(repairTest1.getID()));
//    }
//
//    @Test
//    void repairServiceGetSizeTest() throws RepositoryException, RepairException, HardwareException, ClientException {
//        assertEquals(0, repairService.getPresentSize());
//        assertEquals(0, repairService.getArchiveSize());
//        Repair repairTest1 = repairService.add(client1, hardware1);
//        assertEquals(1, repairService.getPresentSize());
//        assertEquals(0, repairService.getArchiveSize());
//        Repair repairTest2 = repairService.add(client2, hardware2);
//        assertEquals(2, repairService.getPresentSize());
//        assertEquals(0, repairService.getArchiveSize());
//        repairService.repair(repairTest1.getID());
//        assertEquals(1, repairService.getPresentSize());
//        assertEquals(1, repairService.getArchiveSize());
//        repairService.repair(repairTest2.getID());
//        assertEquals(0, repairService.getPresentSize());
//        assertEquals(2, repairService.getArchiveSize());
//    }
//
//    @Test
//    void repairServiceRepairTest() throws RepositoryException, RepairException, HardwareException, ClientException {
//        Repair repairTest1 = repairService.add(client1, hardware1);
//        assertFalse(repairService.get(repairTest1.getID()).isArchive());
//        repairService.repair(repairTest1.getID());
//        assertTrue(repairService.get(repairTest1.getID()).isArchive());
//    }
//
//    @Test
//    void repairServiceRepairArchiveTest() throws RepositoryException, RepairException, HardwareException, ClientException {
//        Repair repairTest1 = repairService.add(client1, hardware1);
//        Repair repairTest2 = repairService.add(client1, hardware2);
//        assertFalse(client1.isArchive());
//        assertFalse(hardware1.isArchive());
//        assertFalse(hardware2.isArchive());
//        repairService.repair(repairTest1.getID());
//        assertFalse(client1.isArchive());
//        assertTrue(hardware1.isArchive());
//        assertFalse(hardware2.isArchive());
//        repairService.repair(repairTest2.getID());
//        assertTrue(client1.isArchive());
//        assertTrue(hardware1.isArchive());
//        assertTrue(hardware2.isArchive());
//    }
//
//    @Test
//    void repairServiceChangeBalanceTest() throws RepositoryException, RepairException, HardwareException, ClientException {
//        Repair repairTest1 = repairService.add(client1, hardware1);
//        Repair repairTest2 = repairService.add(client1, hardware2);
//        assertEquals(0, client1.getBalance());
//        repairService.repair(repairTest1.getID());
//        assertEquals(-4.5, client1.getBalance());
//        repairService.repair(repairTest2.getID());
//        assertEquals(-2164.5, client1.getBalance());
//    }
//}
