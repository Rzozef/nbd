package org.pl.databaseService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.databaseRepository.RepairMongoRepository;
import org.pl.exceptions.ClientException;
import org.pl.exceptions.HardwareException;
import org.pl.exceptions.RepairException;
import org.pl.model.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.pl.model.Condition.DUSTY;

public class RepairMongoServiceTest {
    private RepairMongoService repairMongoService;
    private RepairMongoRepository repairMongoRepository;
    private Address address;
    private Hardware hardware;
    private Client client;

    @BeforeEach
    void setUp() {
        repairMongoRepository = new RepairMongoRepository();
        repairMongoService = new RepairMongoService(repairMongoRepository);
        address = Address.builder()
                .street("White")
                .number("123")
                .city("Lodz")
                .build();
        client = Client.builder()
                .entityId(UUID.randomUUID())
                .personalId("0")
                .clientType(new Basic())
                .phoneNumber("535-535-535")
                .balance(100)
                .firstName("John")
                .lastName("Doe")
                .address(address)
                .repairs(0)
                .build();
        hardware = Hardware.builder()
                .hardwareType(new Computer(DUSTY))
                .price(100)
                .entityId(UUID.randomUUID())
                .build();
    }

    @Test
    void addNegativeTest() {
        assertThrows(RepairException.class,
                ()-> repairMongoService.add(null, hardware));
        assertThrows(RepairException.class,
                ()-> repairMongoService.add(client, null));
        Client clientTest = Client.builder()
                .entityId(client.getEntityId())
                .personalId("0")
                .clientType(new Basic())
                .phoneNumber("535-535-535")
                .balance(100)
                .firstName("John")
                .lastName("Doe")
                .address(address)
                .repairs(2)
                .build();
        assertThrows(ClientException.class,
                ()-> repairMongoService.add(clientTest, hardware));
    }

    @Test
    void addPositiveTest() throws HardwareException, ClientException, RepairException {
        assertTrue(repairMongoService.add(client, hardware));
    }

    @Test
    void getAllRepairsTest() throws HardwareException, ClientException, RepairException {
        assertEquals(0, repairMongoService.getAllRepairs().size());
        assertTrue(repairMongoService.add(client, hardware));
        assertEquals(1, repairMongoService.getAllRepairs().size());
        assertEquals(Repair.class, repairMongoService.getAllRepairs().get(0).getClass());
    }
    @Test
    void getAllClientsRepairsTest() throws HardwareException, ClientException, RepairException {
        assertTrue(repairMongoService.add(client, hardware));
        assertEquals(1, repairMongoService.getAllRepairsByClientId(client.getEntityId()).size());
        assertEquals(Repair.class, repairMongoService.getAllRepairsByClientId(client.getEntityId()).get(0).getClass());
    }

    @Test
    void getRepairByIdTest() throws HardwareException, ClientException, RepairException {
        assertTrue(repairMongoService.add(client, hardware));
        UUID id = repairMongoRepository.findAll().get(0).getEntityId();
        assertEquals(Repair.class, repairMongoService.getRepairById(id).getClass());
    }

    @Test
    void updateArchiveTest() throws HardwareException, ClientException, RepairException {
        assertTrue(repairMongoService.add(client, hardware));
        UUID id = repairMongoService.getAllRepairs().get(0).getEntityId();
        assertEquals(Repair.class, repairMongoService.updateArchive(id).getClass());
        assertTrue(repairMongoService.getRepairById(id).isArchive());
    }


    @Test
    void deleteTest() throws HardwareException, ClientException, RepairException {
        assertTrue(repairMongoService.add(client, hardware));
        UUID id1 = repairMongoService.getAllRepairs().get(0).getEntityId();
        assertTrue(repairMongoService.add(client, hardware));
        UUID id2 = repairMongoService.getAllRepairs().get(1).getEntityId();
        assertTrue(repairMongoService.delete(id1));
        assertEquals(1, repairMongoService.getAllRepairs().size());
        assertFalse(repairMongoService.delete(id1));
        assertTrue(repairMongoService.delete(id2));
        assertEquals(0, repairMongoService.getAllRepairs().size());
    }

    @AfterEach
    void clearDatabase() {
        repairMongoRepository.removeAll();
    }
}
