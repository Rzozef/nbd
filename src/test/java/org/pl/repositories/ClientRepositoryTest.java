package org.pl.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Address;
import org.pl.model.Basic;
import org.pl.model.Client;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ClientRepositoryTest {
    private ClientRepository repository;
    private Client client;
    private Client client1;
    private Address address;
    ArrayList<Client> list;
    private UUID clientUUID1 = UUID.randomUUID();
    private UUID clientUUID2 = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        address = new Address("White", "123", "Lodz");
        client = new Client(clientUUID1, 100, "John", "Doe", "12345678901",
                "535-535-535", new Basic(), address);
        client1 = new Client(clientUUID2, 100, "John", "Doe", "10987654321",
                "535-535-535", new Basic(), address);
        list = new ArrayList<>();
        repository = new ClientRepository(list);
    }

    @Test
    void getElementsTest() {
        assertNotNull(repository.getElements());
        assertEquals(0, repository.getElements().size());
    }

    @Test
    void addTest() throws RepositoryException {
        assertThrows(RepositoryException.class, () -> repository.add(null));
        assertEquals(0, repository.getElements().size());
        repository.add(client);
        assertEquals(1, repository.getElements().size());
        assertNotNull(repository.getElements().get(0));
    }

    @Test
    void archiveTest() throws RepositoryException {
        repository.add(client1);
        repository.archivise(client1.getId());
        assertTrue(client1.isArchive());
    }

    @Test
    void getTest() throws RepositoryException {
        assertThrows(RepositoryException.class, () -> repository.get(client.getId()));
        repository.add(client);
        assertEquals(client, repository.get(client.getId()));
        assertThrows(RepositoryException.class, () -> repository.get(client1.getId()));
    }

    @Test
    void getSizeTest() throws RepositoryException {
        assertEquals(0, repository.getSize(true));
        assertEquals(0, repository.getSize(false));
        repository.add(client);
        assertEquals(1, repository.getSize(true));
        assertEquals(0, repository.getSize(false));
        repository.add(client1);
        assertEquals(2, repository.getSize(true));
        assertEquals(0, repository.getSize(false));
    }

    @Test
    void isArchiveTest() throws RepositoryException {
        repository.add(client);
        assertFalse(repository.isArchive(client.getId()));
        assertThrows(RepositoryException.class, () -> repository.isArchive(client1.getId()));
    }

    @Test
    void unarchiviseTest() throws RepositoryException {
        repository.add(client);
        repository.unarchive(client.getId());
        assertFalse(repository.isArchive(client.getId()));
        assertThrows(RepositoryException.class, () -> repository.unarchive(client1.getId()));
    }
}