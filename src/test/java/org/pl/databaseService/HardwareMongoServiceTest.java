//package org.pl.databaseService;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.pl.databaseRepository.HardwareMongoRepository;
//import org.pl.exceptions.HardwareException;
//import org.pl.exceptions.ServiceException;
//import org.pl.model.Hardware;
//
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class HardwareMongoServiceTest {
//    private static HardwareMongoService hardwareMongoService;
//    private static HardwareMongoRepository hardwareMongoRepository;
//
//    @BeforeAll
//    static void initConnection() {
//        hardwareMongoRepository = new HardwareMongoRepository();
//        hardwareMongoService = new HardwareMongoService(hardwareMongoRepository);
//    }
//
//    @Test
//    void addNegativeTest() {
//        assertThrows(HardwareException.class,
//                ()-> hardwareMongoService.add(false, -2, "Fine", "Computer"));
//        assertThrows(ServiceException.class,
//                () -> hardwareMongoService.add(false, 100, "panie, kto to panu robil", "Computer"));
//        assertThrows(ServiceException.class,
//                () -> hardwareMongoService.add(false, 100, "Fine", "Konkuter"));
//    }
//
//    @Test
//    void addPositiveTest() throws ServiceException, HardwareException {
//        assertTrue(hardwareMongoService.add(false, 100, "Fine", "Computer"));
//    }
//
//    @Test
//    void getAllHardwaresTest() throws HardwareException, ServiceException {
//        assertEquals(0, hardwareMongoService.getAllHardwares().size());
//        assertTrue(hardwareMongoService.add(false, 100, "Fine", "Computer"));
//        assertEquals(1, hardwareMongoService.getAllHardwares().size());
//        assertEquals(Hardware.class, hardwareMongoService.getAllHardwares().get(0).getClass());
//    }
//
//    @Test
//    void getHardwareTest() throws ServiceException, HardwareException {
//        assertTrue(hardwareMongoService.add(false, 100, "Fine", "Computer"));
//        UUID id = hardwareMongoService.getAllHardwares().get(0).getEntityId();
//        assertEquals(Hardware.class, hardwareMongoService.getHardware(id).getClass());
//    }
//
//    @Test
//    void updateArchiveTest() throws ServiceException, HardwareException {
//        assertTrue(hardwareMongoService.add(false, 100, "Fine", "Computer"));
//        UUID id = hardwareMongoService.getAllHardwares().get(0).getEntityId();
//        assertEquals(Hardware.class, hardwareMongoService.updateArchive(id, true).getClass());
//        assertTrue(hardwareMongoService.getHardware(id).isArchive());
//    }
//
//    @Test
//    void updatePriceTest() throws ServiceException, HardwareException {
//        assertTrue(hardwareMongoService.add(false, 100, "Fine", "Computer"));
//        UUID id = hardwareMongoService.getAllHardwares().get(0).getEntityId();
//        assertEquals(Hardware.class, hardwareMongoService.updatePrice(id, 300).getClass());
//        assertEquals(300, hardwareMongoService.getHardware(id).getPrice());
//    }
//
//    @Test
//    void deleteTest() throws ServiceException, HardwareException {
//        assertTrue(hardwareMongoService.add(false, 100, "Fine", "Computer"));
//        UUID id1 = hardwareMongoService.getAllHardwares().get(0).getEntityId();
//        assertTrue(hardwareMongoService.add(false, 200, "Fine", "Computer"));
//        UUID id2 = hardwareMongoService.getAllHardwares().get(1).getEntityId();
//        assertTrue(hardwareMongoService.delete(id1));
//        assertEquals(1, hardwareMongoService.getAllHardwares().size());
//        assertFalse(hardwareMongoService.delete(id1));
//        assertTrue(hardwareMongoService.delete(id2));
//        assertEquals(0, hardwareMongoService.getAllHardwares().size());
//    }
//
//    @AfterEach
//    void clearDatabase() {
//        hardwareMongoRepository.removeAll();
//    }
//}
