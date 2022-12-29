package org.pl.cassandra.converters;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.pl.cassandra.model.ClientCassandra;
import org.pl.cassandra.model.HardwareCassandra;
import org.pl.cassandra.model.RepairCassandra;
import org.pl.cassandra.repositories.ClientCassandraRepository;
import org.pl.cassandra.repositories.HardwareCassandraRepository;
import org.pl.exceptions.ClientException;
import org.pl.exceptions.HardwareException;
import org.pl.model.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class RepairConverterTest {
    private static ClientCassandraRepository clientCassandraRepository;
    private static HardwareCassandraRepository hardwareCassandraRepository;

    @BeforeAll
    static void setUp() {
        clientCassandraRepository = new ClientCassandraRepository();
        hardwareCassandraRepository = new HardwareCassandraRepository();
    }

    @Test
    void convertRepairToRepairCassandraTest() {
        Address address = new Address("Warsaw", "77", "Zielona");
        ClientType clientType = new Basic();
        Client client = new Client(UUID.randomUUID(), 300, "Jan", "Kowalski", "12345678901", "123456789", clientType, address);
        HardwareType computer = new Computer(Condition.FINE);
        Hardware hardware = new Hardware(UUID.randomUUID(), 200, computer, false, "computer");
        Repair repair = new Repair(UUID.randomUUID(), false, client, hardware);
        assertEquals(RepairConverter.toRepositoryModel(repair).getClass(), RepairCassandra.class);
    }

    @Test
    void convertRepairCassandraToRepairTest() throws HardwareException, ClientException {
        UUID clientUUID = UUID.randomUUID();
        UUID hardwareUUID = UUID.randomUUID();
        ClientCassandra clientCassandra = new ClientCassandra(clientUUID, false, 300, "Jan", "Kowalski", "12345678901", "123456789", (float)1.0, 3, "Basic", "Warsaw", "Zielona", "77");
        HardwareCassandra hardwareCassandra = new HardwareCassandra(hardwareUUID, false, 200, "computer", "FINE");
        clientCassandraRepository.create(clientCassandra);
        hardwareCassandraRepository.create(hardwareCassandra);
        RepairCassandra repairCassandra = new RepairCassandra(UUID.randomUUID(), false, clientUUID, hardwareUUID);
        assertEquals(RepairConverter.fromRepositoryModel(repairCassandra).getClass(), Repair.class);
        assertEquals(RepairConverter.fromRepositoryModel(repairCassandra).getClient().getClass(), Client.class);
        assertEquals(RepairConverter.fromRepositoryModel(repairCassandra).getHardware().getClass(), Hardware.class);
    }

    @AfterEach
    void afterEach() {
        clientCassandraRepository.deleteAll();
        hardwareCassandraRepository.deleteAll();
    }

    @AfterAll
    static void tearDown() {
        clientCassandraRepository.close();
        hardwareCassandraRepository.close();
    }
}
