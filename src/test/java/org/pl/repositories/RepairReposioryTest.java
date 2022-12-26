package org.pl.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.exceptions.RepositoryException;
import org.pl.model.*;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.pl.model.Condition.DUSTY;

class RepairReposioryTest {
    private Repository<Repair> repository;
    private Address address;
    private Client client;
    private Hardware hardware;
    private Repair repair;
    private Repair repair1;
    private ArrayList<Repair> list;
    private UUID clientUUID = UUID.randomUUID();
    private UUID hardwareUUID = UUID.randomUUID();
    private UUID repairUUID1 = UUID.randomUUID();
    private UUID repairUUID2 = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        address = Address.builder()
                .street("White")
                .number("123")
                .city("Lodz")
                .build();

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
        hardware = Hardware.builder()
                .archive(false)
                .hardwareType(new Computer(DUSTY))
                .price(100)
                .id(hardwareUUID)
                .build();
        repair = Repair.builder()
                .client(client)
                .hardware(hardware)
                .archive(true)
                .id(repairUUID1)
                .build();
        repair1 = Repair.builder()
                .client(client)
                .hardware(hardware)
                .archive(false)
                .id(repairUUID2)
                .build();
        list = new ArrayList<>();
        repository = new RepairRepository(list);
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
        repository.add(repair);
        assertEquals(1, repository.getElements().size());
        assertNotNull(repository.getElements().get(0));
    }

    @Test
    void archiveTest() throws RepositoryException {
        repository.add(repair);
        assertThrows(RepositoryException.class, () -> repository.archivise(repair.getID()));
        repository.add(repair1);
        repository.archivise(repair1.getID());
        assertTrue(repair1.isArchive());
    }

    @Test
    void getTest() throws RepositoryException {
        assertThrows(RepositoryException.class, () -> repository.get(repair.getID()));
        repository.add(repair);
        assertEquals(repair, repository.get(repair.getID()));
        assertThrows(RepositoryException.class, () -> repository.get(repair1.getID()));
    }

    @Test
    void getSizeTest() throws RepositoryException {
        assertEquals(0, repository.getSize(true));
        assertEquals(0, repository.getSize(false));
        repository.add(repair);
        assertEquals(0, repository.getSize(true));
        assertEquals(1, repository.getSize(false));
        repository.add(repair1);
        assertEquals(1, repository.getSize(true));
        assertEquals(1, repository.getSize(false));
    }

    @Test
    void isArchiveTest() throws RepositoryException {
        repository.add(repair);
        assertTrue(repository.isArchive(repair.getID()));
        assertThrows(RepositoryException.class, () -> repository.isArchive(repair1.getID()));
    }

    @Test
    void unarchiviseTest() throws RepositoryException {
        repository.add(repair);
        repository.unarchive(repair.getID());
        assertFalse(repository.isArchive(repair.getID()));
        assertThrows(RepositoryException.class, () -> repository.unarchive(repair1.getID()));
    }
}
