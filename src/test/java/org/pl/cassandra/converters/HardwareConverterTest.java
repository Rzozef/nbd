package org.pl.cassandra.converters;

import org.junit.jupiter.api.Test;
import org.pl.cassandra.model.HardwareCassandra;
import org.pl.exceptions.HardwareException;
import org.pl.model.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class HardwareConverterTest {

    @Test
    void convertHardwareToHardwareCassandraTest() {
        HardwareType computer = new Computer(Condition.FINE);
        Hardware hardware = new Hardware(UUID.randomUUID(), 200, computer, false, "computer");
        assertEquals(HardwareConverter.toRepositoryModel(hardware).getClass(), HardwareCassandra.class);
        assertEquals(HardwareConverter.toRepositoryModel(hardware).getCondition(), "FINE");
    }

    @Test
    void convertHardwareCassandraToHardwareTest() throws HardwareException {
        HardwareCassandra hardwareCassandra = new HardwareCassandra(UUID.randomUUID(), false, 300, "monitor", "BAD");
        assertEquals(HardwareConverter.fromRepositoryModel(hardwareCassandra).getClass(), Hardware.class);
        assertEquals(HardwareConverter.fromRepositoryModel(hardwareCassandra).getHardwareType().getClass(), Monitor.class);
    }
}
