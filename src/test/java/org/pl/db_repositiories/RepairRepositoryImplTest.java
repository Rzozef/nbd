package org.pl.db_repositiories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.pl.db_repositories.HardwareRepositoryImpl;
import org.pl.db_repositories.RepairRepositoryImpl;
import org.pl.model.*;

import java.util.ArrayList;
import java.util.List;

public class RepairRepositoryImplTest {
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
    void saveRepairTest() {
        RepairRepositoryImpl repository = new RepairRepositoryImpl(em);
        HardwareType htype = new Monitor(Condition.DUSTY);
        Hardware hardware = Hardware.builder().hardwareType(htype).price(20).archive(false).build();
        ClientType ctype = new Basic();
        Address address = Address.builder().city("a").number("1").street("b").build();
        Client client = Client.builder().clientType(ctype).address(address).
                firstName("a").lastName("b").personalId("a").phoneNumber("c").balance(100.0).archive(false).build();
        Repair repair = Repair.builder().hardware(hardware).client(client).archive(false).build();
        repository.saveRepair(repair);
        Assertions.assertTrue(em.contains(repair));
        repair.setArchive(true);
        repository.saveRepair(repair);
        Assertions.assertTrue(repository.getRepairById(repair.getID()).isArchive());
    }

    @Test
    void deleteRepairTest() {
        RepairRepositoryImpl repository = new RepairRepositoryImpl(em);
        HardwareType htype = new Monitor(Condition.DUSTY);
        Hardware hardware = Hardware.builder().hardwareType(htype).price(20).archive(false).build();
        ClientType ctype = new Basic();
        Address address = Address.builder().city("a").number("1").street("b").build();
        Client client = Client.builder().clientType(ctype).address(address).
                firstName("a").lastName("b").personalId("a").phoneNumber("c").balance(100.0).archive(false).build();
        Repair repair = Repair.builder().hardware(hardware).client(client).archive(false).build();
        Repair repair2 = Repair.builder().hardware(hardware).client(client).archive(true).build();
        repository.saveRepair(repair);
        repository.saveRepair(repair2);
        Assertions.assertTrue(em.contains(repair) && em.contains((repair)));
        repository.deleteRepair(repair2);
        Assertions.assertFalse(em.contains(repair2));
    }

    @Test
    void getRepairByIdTest() {
        RepairRepositoryImpl repository = new RepairRepositoryImpl(em);
        HardwareType htype = new Monitor(Condition.DUSTY);
        Hardware hardware = Hardware.builder().hardwareType(htype).price(20).archive(false).build();
        ClientType ctype = new Basic();
        Address address = Address.builder().city("a").number("1").street("b").build();
        Client client = Client.builder().clientType(ctype).address(address).
                firstName("a").lastName("b").personalId("a").phoneNumber("c").balance(100.0).archive(false).build();
        Repair repair = Repair.builder().hardware(hardware).client(client).archive(false).build();
        Repair repair2 = Repair.builder().hardware(hardware).client(client).archive(true).build();
        repository.saveRepair(repair);
        repository.saveRepair(repair2);
        Assertions.assertEquals(repair, repository.getRepairById(repair.getID()));
        Assertions.assertNotEquals(repair2, repository.getRepairById(repair.getID()));
    }
}
