package org.pl.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.exceptions.HardwareException;
import org.pl.exceptions.RepositoryException;
import org.pl.exceptions.ServiceException;
import org.pl.model.*;
import org.pl.repositories.HardwareRepository;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class HardwareServiceTest {
    HardwareType computer, console, monitor, phone;
    ArrayList<Hardware> hardwares;
    HardwareRepository hardwareRepository;
    HardwareService hardwareService;

    @BeforeEach
    void setUp() {
        computer = new Computer(Condition.FINE);
        console = new Console(Condition.VERY_BAD);
        monitor = new Monitor(Condition.DUSTY);
        phone = new Phone(Condition.UNREPAIRABLE);
        hardwares = new ArrayList<>();
        hardwareRepository = new HardwareRepository(hardwares);
        hardwareService = new HardwareService(hardwareRepository);
    }

    @Test
    void hardwareServiceAddPositiveTest() throws ServiceException, RepositoryException, HardwareException {
        Hardware hardwareTest1 = hardwareService.add(1000, "computer", Condition.FINE);
        assertNotNull(hardwareService.get(hardwareTest1.getID()));
        Hardware hardwareTest2 = hardwareService.add(100, "console", Condition.VERY_BAD);
        assertNotNull(hardwareService.get(hardwareTest2.getID()));
        Hardware hardwareTest3 = hardwareService.add(10, "monitor", Condition.DUSTY);
        assertNotNull(hardwareService.get(hardwareTest3.getID()));
        Hardware hardwareTest4 = hardwareService.add(10, "phone", Condition.UNREPAIRABLE);
        assertNotNull(hardwareService.get(hardwareTest4.getID()));
    }

    @Test
    void hardwareServiceAddNegativeTest() {
        assertThrows(ServiceException.class,
                ()-> hardwareService.add(1000, "Camputer", Condition.UNREPAIRABLE));
        assertThrows(HardwareException.class,
                ()-> hardwareService.add(-100, "console", Condition.UNREPAIRABLE));
        assertThrows(ServiceException.class,
                ()-> hardwareService.add(10, "mOndsItOr", Condition.UNREPAIRABLE));
        assertThrows(ServiceException.class,
                ()-> hardwareService.add(10, "mOndsItOr", "verygood"));
    }

    @Test
    void hardwareServiceGetInfoTest() throws ServiceException, HardwareException, RepositoryException {
        Hardware hardwareTest1 = hardwareService.add(200, "monitor", "fine");
        String expectedInfo = "Hardware(archive=false, price=200, hardwareType=Monitor(condition=Condition.FINE))";
        assertEquals(expectedInfo, hardwareService.getInfo(hardwareTest1.getID()));
    }

    @Test
    void hardwareServiceRemovePositiveTest() throws HardwareException, RepositoryException {
        Hardware hardwareTest1 = hardwareService.add(100, computer);
        assertEquals(1, hardwareService.getPresentSize());
        Hardware hardwareTest2 = hardwareService.add(1000, console);
        assertEquals(2, hardwareService.getPresentSize());
        hardwareService.remove(hardwareTest2.getID());
        assertEquals(1, hardwareService.getPresentSize());
        assertTrue(hardwareService.get(hardwareTest2.getID()).isArchive());
        hardwareService.remove(hardwareTest1.getID());
        assertEquals(0, hardwareService.getPresentSize());
        assertTrue(hardwareService.get(hardwareTest1.getID()).isArchive());
    }

    @Test
    void hardwareServiceRemoveNegativeTest() throws HardwareException, RepositoryException {
        Hardware hardwareTest1 = hardwareService.add(1000, phone);
        Hardware hardwareTest2 = hardwareService.add(1000, computer);
        hardwareService.remove(hardwareTest2.getID());
        assertThrows(RepositoryException.class,
                ()-> hardwareService.remove(hardwareTest2.getID()));
    }

    @Test
    void hardwareServiceGetSizeTest() throws RepositoryException, HardwareException {
        assertEquals(0, hardwareService.getPresentSize());
        assertEquals(0, hardwareService.getArchiveSize());
        Hardware hardwareTest1 = hardwareService.add(100, monitor);
        assertEquals(1, hardwareService.getPresentSize());
        assertEquals(0, hardwareService.getArchiveSize());
        Hardware hardwareTest2 = hardwareService.add(1000, phone);
        assertEquals(2, hardwareService.getPresentSize());
        assertEquals(0, hardwareService.getArchiveSize());
        hardwareService.remove(hardwareTest1.getID());
        assertEquals(1, hardwareService.getPresentSize());
        assertEquals(1, hardwareService.getArchiveSize());
        hardwareService.remove(hardwareTest2.getID());
        assertEquals(0, hardwareService.getPresentSize());
        assertEquals(2, hardwareService.getArchiveSize());
    }
}
