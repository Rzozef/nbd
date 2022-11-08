package org.pl.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.exceptions.ClientException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Address;
import org.pl.model.Client;
import org.pl.model.Repair;
import org.pl.repositories.ClientRepository;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ClientServiceTest {
    ClientRepository clientRepository;
    Address address;
    ArrayList<Client> clientsList;
    ClientService clientService;

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
        Client clientTest1 = clientService.add("Andrzej", "Niesapkowski", "603462321","10", address);
        assertNotNull(clientService.get(clientTest1.getID()));
        Client clientTest2 = clientService.add("Anna", "Annowska", "124464322", "10", address);
        assertNotNull(clientService.get(clientTest2.getID()));
        Client clientTest3 = clientService.add("Jaskier", "Uzdolniony", "222222222", "10", address);
        assertNotNull(clientService.get(clientTest3.getID()));
        Client clientTest4 = clientService.add("Pepe", "Monocyklowy", "121424622", "10", address);
        assertNotNull(clientService.get(clientTest4.getID()));
    }

    @Test
    void clientServiceAddNegativeTest() {
        assertThrows(ClientException.class,
                ()-> clientService.add("", "Niesapkowski", "603462321", "10", address));
        assertThrows(ClientException.class,
                ()-> clientService.add("Anna", "", "603462321", "10", address));
        assertThrows(ClientException.class,
                ()-> clientService.add("Andrzej", "Niesapkowski", "", "10", address));
        assertThrows(ClientException.class,
                ()-> clientService.add("Andrzej", "Niesapkowski", "111111111", "", address));
        assertThrows(ClientException.class,
                ()-> clientService.add("Andrzej", "Niesapkowski", "603462321", "10", null));
    }

    @Test
    void clientServiceToStringTest() throws RepositoryException, ClientException {
        Client clientTest1 = clientService.add("Pan", "Tadeusz", "280618349", "10", address);
        String expectedInfo = "Client(archive=false, balance=0.0, firstName=Pan, lastName=Tadeusz, personalId=10, phoneNumber=280618349, clientType=null, address=Address(city=Lodz, number=123, street=White), repairs=0)";
        assertEquals(expectedInfo, clientService.getInfo(clientTest1.getID()));
    }

    @Test
    void clientServiceRemovePositiveTest() throws RepositoryException, ClientException {
        Address address2 = Address.builder()
                         .city("Katowice")
                         .street("Porajska")
                         .number("34")
                         .build();
        Client clientTest1 = clientService.add("Jakub", "Stokrotka", "5876321210", "10", address);
        assertEquals(1, clientService.getPresentSize());
        Client clientTest2 = clientService.add("Marcin", "Steczkowski", "8765432109", "10", address2);
        assertEquals(2, clientService.getPresentSize());
        clientService.remove(clientTest1.getID());
        assertEquals(1, clientService.getPresentSize());
        assertTrue(clientService.get(clientTest1.getID()).isArchive());
        assertFalse(clientService.get(clientTest2.getID()).isArchive());
        clientService.remove(clientTest2.getID());
        assertEquals(0, clientService.getPresentSize());
    }

    @Test
    void clientServiceRemoveNegativeTest() throws RepositoryException, ClientException {
        Address address2 = Address.builder()
                .city("Katowice")
                .street("Porajska")
                .number("34")
                .build();
        Client clientTest1 = clientService.add("Jakub", "Stokrotka", "5876321210", "10", address);
        Client clientTest2 = clientService.add("Marcin", "Steczkowski", "8765432109", "10", address2);
        clientService.remove(clientTest1.getID());
        assertThrows(RepositoryException.class,
                ()-> clientService.remove(clientTest1.getID()));
    }

    @Test
    void clientServiceGetSizeTest() throws RepositoryException, ClientException {

        assertEquals(0, clientService.getPresentSize());
        assertEquals(0, clientService.getArchiveSize());
        Client clientTest1 = clientService.add("Kuba", "Jakubowski", "1234567890", "10", address);
        assertEquals(1, clientService.getPresentSize());
        assertEquals(0, clientService.getArchiveSize());
        Address address2 = Address.builder()
                .city("Katowice")
                .street("Porajska")
                .number("34")
                .build();
        Client clientTest2 = clientService.add("Piotr", "Piotrkowski", "0987654321", "10", address2);
        assertEquals(2, clientService.getPresentSize());
        assertEquals(0, clientService.getArchiveSize());
        clientService.remove(clientTest1.getID());
        assertEquals(1, clientService.getPresentSize());
        assertEquals(1, clientService.getArchiveSize());
        clientService.remove(clientTest2.getID());
        assertEquals(0, clientService.getPresentSize());
        assertEquals(2, clientService.getArchiveSize());
    }

    @Test
    void clientServiceGetClientBalanceTest() throws RepositoryException, ClientException {
        Client clientTest1 = clientService.add("Adam", "Adamowski", "1234565432", "10", address);
        assertEquals(0, clientService.getClientBalance(clientTest1.getID()));
        clientService.get(clientTest1.getID()).changeBalance(100);
        assertEquals(100, clientService.getClientBalance(clientTest1.getID()));
    }
}
