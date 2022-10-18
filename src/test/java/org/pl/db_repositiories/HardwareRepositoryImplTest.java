package org.pl.db_repositiories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.pl.db_repositories.HardwareRepositoryImpl;
import org.pl.model.Condition;
import org.pl.model.Hardware;
import org.pl.model.HardwareType;
import org.pl.model.Monitor;


public class HardwareRepositoryImplTest {
    private static EntityManagerFactory emf;
    private static EntityManager em;

    @BeforeAll
    static void beforeAll() {
        emf = Persistence.createEntityManagerFactory("POSTGRES_REPAIR_PU");
        em = emf.createEntityManager();
    }

    @AfterAll
    static void afterAll() {
        if (emf != null) {
            emf.close();
        }
    }

    @Test
    void saveHardwareTest() {
        HardwareRepositoryImpl repository = new HardwareRepositoryImpl(em);
        HardwareType type = new Monitor(Condition.DUSTY);
        Hardware hardware = Hardware.builder().hardwareType(type).price(20).archive(false).build();
        repository.saveHardware(hardware);
        Assertions.assertTrue(em.contains(hardware));
        hardware.setPrice(10);
        repository.saveHardware(hardware);
        Assertions.assertEquals(10.0, repository.getHardwareById(hardware.getID()).getPrice());
    }

    @Test
    void deleteHardwareTest() {
        HardwareRepositoryImpl repository = new HardwareRepositoryImpl(em);
        HardwareType type = new Monitor(Condition.DUSTY);
        Hardware hardware = Hardware.builder().hardwareType(type).price(20).archive(false).build();
        Hardware hardware2 = Hardware.builder().hardwareType(type).price(10).archive(false).build();
        repository.saveHardware(hardware);
        repository.saveHardware(hardware2);
        Assertions.assertTrue(em.contains(hardware) && em.contains((hardware2)));
        repository.deleteHardware(hardware2);
        Assertions.assertFalse(em.contains(hardware2));
    }

    @Test
    void getHardwareByIdTest() {
        HardwareRepositoryImpl repository = new HardwareRepositoryImpl(em);
        HardwareType type = new Monitor(Condition.DUSTY);
        Hardware hardware = Hardware.builder().hardwareType(type).price(20).archive(false).build();
        Hardware hardware2 = Hardware.builder().hardwareType(type).price(10).archive(false).build();
        repository.saveHardware(hardware);
        repository.saveHardware(hardware2);
        Assertions.assertEquals(hardware, repository.getHardwareById(hardware.getID()));
        Assertions.assertNotEquals(hardware2, repository.getHardwareById(hardware.getID()));
    }
}