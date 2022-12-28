package org.pl.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.exceptions.ClientException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    private Client client;
    private Address address;
    private UUID clientUUID = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        address = new Address("White", "123", "Lodz");
        client = new Client(clientUUID, 100, "John", "Doe", "12345678901",
                "535-535-535", new Basic(), address);
    }

    @Test
    void calculateDiscount() throws ClientException {
        assertEquals(0.0, client.calculateDiscount(100));
    }

    @Test
    void changeBalance() {
        assertEquals(100, client.getBalance());
        client.changeBalance(100);
        assertEquals(200, client.getBalance());
        client.changeBalance(-200);
        assertEquals(0, client.getBalance());
    }

    @Test
    void setArchive() {
        client.setArchive(true);
        assertTrue(client.isArchive());
        client.setArchive(false);
        assertFalse(client.isArchive());
    }

    @Test
    void isArchive() {
        assertFalse(client.isArchive());
    }

    @Test
    void getBalance() {
        assertEquals(100, client.getBalance());
    }

    @Test
    void getFirstName() {
        assertEquals("John", client.getFirstName());
    }

    @Test
    void getLastName() {
        assertEquals("Doe", client.getLastName());
    }

    @Test
    void getPersonalId() {
        assertEquals("12345678901", client.getPersonalId());
    }

    @Test
    void getPhoneNumber() {
        assertEquals("535-535-535", client.getPhoneNumber());
    }

    @Test
    void getClientType() {
        assertEquals(new Basic(), client.getClientType());
    }

    @Test
    void getAddress() {
        assertEquals(address, client.getAddress());
    }

    @Test
    void setBalance() {
        client.setBalance(200);
        assertEquals(200, client.getBalance());
    }

    @Test
    void setFirstName() {
        client.setFirstName("Ian");
        assertEquals("Ian", client.getFirstName());
    }

    @Test
    void setLastName() {
        client.setLastName("Nowak");
        assertEquals("Nowak", client.getLastName());
    }

    @Test
    void setPersonalId() {
        client.setPersonalId("09876543212");
        assertEquals("09876543212", client.getPersonalId());
    }

    @Test
    void setPhoneNumber() {
        client.setPhoneNumber("200-200-200");
        assertEquals("200-200-200", client.getPhoneNumber());
    }

    @Test
    void setClientType() {
       client.setClientType(new Vip());
       assertEquals(new Vip(), client.getClientType());
    }

    @Test
    void setAddress() {
        client.setAddress(new Address("Lublin", "123", "Green"));
        assertNotEquals(address, client.getAddress());
    }

    @Test
    void testEquals() {
        Client sameClient = new Client(clientUUID, 100, "John", "Doe", "12345678901",
                "535-535-535", new Basic(), address);
        Client differentClient = new Client(UUID.randomUUID(), 100, "John", "Doe", "098761234565",
                "111-111-111", new Basic(), address);
        assertEquals(sameClient, client);
        assertNotEquals(differentClient, client);
    }

}