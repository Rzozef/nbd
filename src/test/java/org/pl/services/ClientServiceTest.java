package org.pl.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.exceptions.ClientException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Address;
import org.pl.model.Client;
import org.pl.repositories.ClientRepository;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ClientServiceTest {
    private ClientRepository clientRepository;
    private Address address;
    private ArrayList<Client> clientsList;
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        address = Address.builder()
                .street("White")
                .number("123")
                .city("Lodz")
                .build();
        clientsList = new ArrayList<>();
        clientRepository = new ClientRepository(clientsList);
        clientService = new ClientService(clientRepository);
    }

    @Test
    void clientServiceAddPositiveTest() throws RepositoryException, ClientException {
        Client client1 = clientService.add("Andrzej", "Niesapkowski", "12345678909", "603462321", address);
        assertNotNull(clientService.get(client1.getID()));
        Client client2 = clientService.add("Anna", "Annowska", "12345678909", "124464322", address);
        assertNotNull(clientService.get(client2.getID()));
        Client client3 = clientService.add("Jaskier", "Uzdolniony", "12345678909", "222222222", address);
        assertNotNull(clientService.get(client3.getID()));
        Client client4 = clientService.add("Pepe", "Monocyklowy", "12345678909", "121424622", address);
        assertNotNull(clientService.get(client4.getID()));
    }

    @Test
    void clientServiceAddNegativeTest() {
        assertThrows(ClientException.class,
                ()-> clientService.add("", "Niesapkowski", "12345678909", "603462321", address));
        assertThrows(ClientException.class,
                ()-> clientService.add("Anna", "", "12345678909", "603462321", address));
        assertThrows(ClientException.class,
                ()-> clientService.add("Andrzej", "Niesapkowski", "", "603462321", address));
        assertThrows(ClientException.class,
                ()-> clientService.add("Andrzej", "Niesapkowski", "12345678909", "603462321", null));
    }

    @Test
    void clientServiceToStringTest() throws RepositoryException, ClientException {
        Client client = clientService.add("Pan", "Tadeusz", "12345678909", "280618349", address);
        String expectedInfo = "Client(id=" + client.getID() + ", archive=false, balance=0.0, firstName=Pan, lastName=Tadeusz, personalId=12345678909, phoneNumber=280618349, clientType=null, address=Address(city=Lodz, number=123, street=White))";
        assertEquals(expectedInfo, clientService.getInfo(client.getID()));
    }

    @Test
    void clientServiceRemovePositiveTest() throws RepositoryException, ClientException {
        Address address2 = Address.builder()
                         .city("Katowice")
                         .street("Porajska")
                         .number("34")
                         .build();
        Client client1 = clientService.add("Jakub", "Stokrotka", "12345678909", "5876321210", address);
        assertEquals(1, clientService.getPresentSize());
        Client client2 = clientService.add("Marcin", "Steczkowski", "12345678909", "8765432109", address2);
        assertEquals(2, clientService.getPresentSize());
        clientService.remove(client1.getID());
        assertEquals(1, clientService.getPresentSize());
        assertTrue(clientService.get(client1.getID()).isArchive());
        assertFalse(clientService.get(client2.getID()).isArchive());
        clientService.remove(client2.getID());
        assertEquals(0, clientService.getPresentSize());
    }

    @Test
    void clientServiceRemoveNegativeTest() throws RepositoryException, ClientException {
        Address address2 = Address.builder()
                .city("Katowice")
                .street("Porajska")
                .number("34")
                .build();
        Client client1 = clientService.add("Jakub", "Stokrotka", "12345678909", "5876321210", address);
        Client client2 = clientService.add("Marcin", "Steczkowski", "12345678909", "8765432109", address2);
        clientService.remove(client1.getID());
        assertThrows(RepositoryException.class,
                ()-> clientService.remove(client1.getID()));
    }

    @Test
    void clientServiceGetSizeTest() throws RepositoryException, ClientException {

        assertEquals(0, clientService.getPresentSize());
        assertEquals(0, clientService.getArchiveSize());
        Client client1 = clientService.add("Kuba", "Jakubowski", "12345678909", "1234567890", address);
        assertEquals(1, clientService.getPresentSize());
        assertEquals(0, clientService.getArchiveSize());
        Address address2 = Address.builder()
                .city("Katowice")
                .street("Porajska")
                .number("34")
                .build();
        Client client2 = clientService.add("Piotr", "Piotrkowski", "12345678909", "0987654321", address2);
        assertEquals(2, clientService.getPresentSize());
        assertEquals(0, clientService.getArchiveSize());
        clientService.remove(client1.getID());
        assertEquals(1, clientService.getPresentSize());
        assertEquals(1, clientService.getArchiveSize());
        clientService.remove(client2.getID());
        assertEquals(0, clientService.getPresentSize());
        assertEquals(2, clientService.getArchiveSize());
    }

    @Test
    void clientServiceGetClientBalanceTest() throws RepositoryException, ClientException {
        Client client = clientService.add("Adam", "Adamowski", "12345678909", "1234565432", address);
        assertEquals(0, clientService.getClientBalance(client.getID()));
        clientService.get(client.getID()).changeBalance(100);
        assertEquals(100, clientService.getClientBalance(client.getID()));
    }
}
