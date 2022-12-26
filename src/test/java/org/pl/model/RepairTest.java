package org.pl.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.exceptions.HardwareException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.pl.model.Condition.*;

class RepairTest {

    private Repair repair;
    private Client client;
    private Hardware hardware;
    private Address address;
    private UUID clientUUID = UUID.randomUUID();
    private UUID hardwareUUID = UUID.randomUUID();
    private UUID repairUUID = UUID.randomUUID();

    @BeforeEach
    void setUp() {
       address = new Address("Warsaw", "123", "White");

        client = Client.builder()
                .id(clientUUID)
                .clientType(new Premium())
                .address(address)
                .balance(300.0)
                .firstName("John")
                .lastName("Doe")
                .personalId("12345678901")
                .phoneNumber("123-123-123")
                .archive(false)
                .build();
        hardware = new Hardware(hardwareUUID, false, 100, new Computer(DUSTY));

        repair = Repair.builder()
                .client(client)
                .hardware(hardware)
                .archive(false)
                .id(repairUUID)
                .build();
    }

    @Test
    void calculateRepairCost() throws HardwareException {
        assertEquals(5.0, repair.calculateRepairCost());
    }

    @Test
    void setArchive() {
        repair.setArchive(false);
        assertFalse(repair.isArchive());
        repair.setArchive(true);
        assertTrue(repair.isArchive());
    }

    @Test
    void getID() {
        assertEquals(repairUUID, repair.getID());
    }

    @Test
    void isArchive() {
        assertFalse(repair.isArchive());
    }

    @Test
    void getClient() {
        assertEquals(client, repair.getClient());
    }

    @Test
    void getHardware() {
        assertEquals(hardware, repair.getHardware());
    }

    @Test
    void setId() {
        UUID newUUID = UUID.randomUUID();
       repair.setId(newUUID);
       assertEquals(newUUID, repair.getID());
    }

    @Test
    void setClient() {
        Client newClient = Client.builder()
                .id(UUID.randomUUID())
                .clientType(new Vip())
                .address(address)
                .balance(300.0)
                .firstName("John")
                .lastName("Doe")
                .personalId("9098765432")
                .phoneNumber("123-123-123")
                .archive(false)
                .build();
        repair.setClient(newClient);
        assertEquals(newClient, repair.getClient());
        assertNotEquals(client, repair.getClient());
    }

    @Test
    void setHardware() {
       Hardware newHardware = new Hardware(UUID.randomUUID(), false, 100, new Computer(DUSTY));
       repair.setHardware(newHardware);
       assertEquals(newHardware, repair.getHardware());
       assertNotEquals(hardware, repair.getHardware());
    }

    @Test
    void testEquals() {
        Client newClient = Client.builder()
                .id(clientUUID)
                .clientType(new Premium())
                .address(address)
                .balance(300.0)
                .firstName("John")
                .lastName("Doe")
                .personalId("12345678901")
                .phoneNumber("123-123-123")
                .archive(false)
                .build();
        Hardware newHardware = new Hardware(hardwareUUID, false, 100, new Computer(DUSTY));

        Repair newRepair = Repair.builder()
                .client(newClient)
                .hardware(newHardware)
                .archive(false)
                .id(repairUUID)
                .build();
        assertEquals(newRepair, repair);
        newRepair.setArchive(true);
        assertNotEquals(newRepair, repair);
    }

}