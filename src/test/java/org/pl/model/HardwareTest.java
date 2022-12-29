package org.pl.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.pl.model.Condition.*;

class HardwareTest {

    private Hardware hardware;
    private UUID hardwareUUID = UUID.randomUUID();

    @BeforeEach
    void setUp() {

        hardware = new Hardware(100, new Computer(DUSTY),false, hardwareUUID, "");
    }

    @Test
    void repair() {
        assertEquals(DUSTY, hardware.getHardwareType().getCondition());
        hardware.repair();
        assertEquals(FINE, hardware.getHardwareType().getCondition());
    }

    @Test
    void setArchive() {
        assertFalse(hardware.isArchive());
        hardware.setArchive(true);
        assertTrue(hardware.isArchive());
        hardware.setArchive(true);
        assertTrue(hardware.isArchive());
        hardware.setArchive(false);
        assertFalse(hardware.isArchive());
        hardware.setArchive(false);
        assertFalse(hardware.isArchive());
    }

    @Test
    void getID() {
        assertEquals(hardwareUUID, hardware.getID());
    }

    @Test
    void isArchive() {
        assertFalse(hardware.isArchive());
    }

    @Test
    void getPrice() {
        assertEquals(100, hardware.getPrice());
    }

    @Test
    void getHardwareType() {
        assertEquals(new Computer(DUSTY), hardware.getHardwareType());
    }

    @Test
    void setPrice() {
        hardware.setPrice(500);
        assertEquals(500, hardware.getPrice());
        hardware.setPrice(900);
        assertEquals(900, hardware.getPrice());
    }

    @Test
    void setHardwareType() {
        hardware.setHardwareType(new Monitor(UNREPAIRABLE));
        assertEquals(new Monitor(UNREPAIRABLE), hardware.getHardwareType());
        hardware.setHardwareType(new Phone(FINE));
        assertEquals(new Phone(FINE), hardware.getHardwareType());
    }

    @Test
    void testEquals() {
        UUID newUUID = UUID.randomUUID();
        assertEquals(new Hardware(100, new Computer(DUSTY),false, hardwareUUID, ""), hardware);
        assertNotEquals(new Hardware(100, new Computer(DUSTY), true, newUUID, ""), hardware);
        assertNotEquals(new Hardware(120, new Computer(DUSTY),false, hardwareUUID, ""), hardware);
        assertNotEquals(new Hardware(100, new Computer(FINE),false, hardwareUUID, ""), hardware);
        assertNotEquals(new Hardware(100, new Phone(DUSTY), false, hardwareUUID, ""), hardware);
    }

    @Test
    void testToString() {
        assertEquals("Hardware{id=" + hardwareUUID + ", archive=false, price=100, hardwareType=Computer{condition=DUSTY}}", hardware.toString());
    }
}