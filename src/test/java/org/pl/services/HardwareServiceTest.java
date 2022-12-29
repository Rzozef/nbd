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
    private HardwareType computer, console, monitor, phone;
    private ArrayList<Hardware> hardwareList;
    private HardwareRepository hardwareRepository;
    private HardwareService hardwareService;

    @BeforeEach
    void setUp() {
        computer = new Computer(Condition.FINE);
        console = new Console(Condition.VERY_BAD);
        monitor = new Monitor(Condition.DUSTY);
        phone = new Phone(Condition.UNREPAIRABLE);
        hardwareList = new ArrayList<>();
        hardwareRepository = new HardwareRepository(hardwareList);
        hardwareService = new HardwareService(hardwareRepository);
    }

    @Test
    void hardwareServiceAddPositiveTest() throws ServiceException, RepositoryException, HardwareException {
        Hardware hardware1 = hardwareService.add(1000, "computer", Condition.FINE);
        assertNotNull(hardwareService.get(hardware1.getId()));
        Hardware hardware2 = hardwareService.add(100, "console", Condition.VERY_BAD);
        assertNotNull(hardwareService.get(hardware2.getId()));
        Hardware hardware3 = hardwareService.add(10, "monitor", Condition.DUSTY);
        assertNotNull(hardwareService.get(hardware3.getId()));
        Hardware hardware4 = hardwareService.add(10, "phone", Condition.UNREPAIRABLE);
        assertNotNull(hardwareService.get(hardware4.getId()));
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
        Hardware hardware = hardwareService.add(200, "monitor", "fine");
        String expectedInfo = "Hardware{id=" + hardware.getId() + ", archive=false, price=200, hardwareType=Monitor{condition=FINE}}";
        assertEquals(expectedInfo, hardwareService.getInfo(hardware.getId()));
    }

    @Test
    void hardwareServiceRemovePositiveTest() throws HardwareException, RepositoryException {
        Hardware hardware1 = hardwareService.add(100, computer);
        assertEquals(1, hardwareService.getPresentSize());
        Hardware hardware2 = hardwareService.add(1000, console);
        assertEquals(2, hardwareService.getPresentSize());
        hardwareService.remove(hardware1.getId());
        assertEquals(1, hardwareService.getPresentSize());
        assertTrue(hardwareService.get(hardware1.getId()).isArchive());
        hardwareService.remove(hardware2.getId());
        assertEquals(0, hardwareService.getPresentSize());
        assertTrue(hardwareService.get(hardware2.getId()).isArchive());
    }

    @Test
    void hardwareServiceRemoveNegativeTest() throws HardwareException, RepositoryException {
        Hardware hardware1 = hardwareService.add(1000, phone);
        Hardware hardware2 = hardwareService.add(1000, computer);
        hardwareService.remove(hardware1.getId());
        assertThrows(RepositoryException.class,
                ()-> hardwareService.remove(hardware1.getId()));
    }

    @Test
    void hardwareServiceGetSizeTest() throws RepositoryException, HardwareException {
        assertEquals(0, hardwareService.getPresentSize());
        assertEquals(0, hardwareService.getArchiveSize());
        Hardware hardware1 = hardwareService.add(100, monitor);
        assertEquals(1, hardwareService.getPresentSize());
        assertEquals(0, hardwareService.getArchiveSize());
        Hardware hardware2 = hardwareService.add(1000, phone);
        assertEquals(2, hardwareService.getPresentSize());
        assertEquals(0, hardwareService.getArchiveSize());
        hardwareService.remove(hardware1.getId());
        assertEquals(1, hardwareService.getPresentSize());
        assertEquals(1, hardwareService.getArchiveSize());
        hardwareService.remove(hardware2.getId());
        assertEquals(0, hardwareService.getPresentSize());
        assertEquals(2, hardwareService.getArchiveSize());
    }
}
