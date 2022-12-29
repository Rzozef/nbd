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

public class HardwareRepositoryTest {
    private HardwareRepository repository;
    private Hardware hardware;
    private Hardware hardware1;
    private ArrayList<Hardware> list;

    @BeforeEach
    void setUp() {
        hardware = new Hardware(100, new Computer(DUSTY),true, UUID.randomUUID(), "");

        hardware1 = new Hardware(100, new Computer(DUSTY),false, UUID.randomUUID(), "");
        list = new ArrayList<>();
        repository = new HardwareRepository(list);
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
        repository.add(hardware);
        assertEquals(1, repository.getElements().size());
        assertNotNull(repository.getElements().get(0));
    }

    @Test
    void archiviseTest() throws RepositoryException {
        repository.add(hardware);
        assertThrows(RepositoryException.class, () -> repository.archivise(hardware.getID()));
        repository.add(hardware1);
        repository.archivise(hardware1.getID());
        assertTrue(hardware1.isArchive());
    }

    @Test
    void getTest() throws RepositoryException {
        assertThrows(RepositoryException.class, () -> repository.get(hardware.getID()));
        repository.add(hardware);
        assertEquals(hardware, repository.get(hardware.getID()));
        assertThrows(RepositoryException.class, () -> repository.get(hardware1.getID()));
    }

    @Test
    void getSizeTest() throws RepositoryException {
        assertEquals(0, repository.getSize(true));
        assertEquals(0, repository.getSize(false));
        repository.add(hardware);
        assertEquals(0, repository.getSize(true));
        assertEquals(1, repository.getSize(false));
        repository.add(hardware1);
        assertEquals(1, repository.getSize(true));
        assertEquals(1, repository.getSize(false));
    }

    @Test
    void isArchiveTest() throws RepositoryException {
        repository.add(hardware);
        assertTrue(repository.isArchive(hardware.getID()));
        assertThrows(RepositoryException.class, () -> repository.isArchive(hardware1.getID()));
    }

    @Test
    void unarchiviseTest() throws RepositoryException {
        repository.add(hardware);
        repository.unarchive(hardware.getID());
        assertFalse(repository.isArchive(hardware.getID()));
        assertThrows(RepositoryException.class, () -> repository.unarchive(hardware1.getID()));
    }
}
