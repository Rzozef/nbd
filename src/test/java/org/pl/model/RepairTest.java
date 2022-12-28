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

        client = new Client(clientUUID, 300.0, "John", "Doe", "12345678901",
                "123-123-123", new Premium(), address);
        hardware = new Hardware(hardwareUUID, false, 100, new Computer(DUSTY));

        repair = new Repair(repairUUID, false, client, hardware);
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
    void setClient() {
        Client newClient = new Client(UUID.randomUUID(), 300.0, "John", "Doe", "9098765432",
                "123-123-123", new Vip(), address);
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
        Client newClient = new Client(clientUUID, 300.0, "John", "Doe", "12345678901",
                "123-123-123", new Premium(), address);
        Hardware newHardware = new Hardware(hardwareUUID, false, 100, new Computer(DUSTY));

        Repair newRepair = new Repair(repairUUID, false, newClient, newHardware);

        assertEquals(newRepair, repair);
        newRepair.setArchive(true);
        assertNotEquals(newRepair, repair);
    }

}