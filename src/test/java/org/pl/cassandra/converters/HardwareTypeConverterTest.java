package org.pl.cassandra.converters;

import org.junit.jupiter.api.Test;
import org.pl.cassandra.model.HardwareCassandra;
import org.pl.exceptions.HardwareException;
import org.pl.model.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class HardwareTypeConverterTest {

    @Test
    void convertHardwareTypeCassandraToHardwareTypeTest() throws HardwareException {
        HardwareCassandra computerCassandra = new HardwareCassandra(UUID.randomUUID(), false, 200, "computer", "FINE");
        HardwareCassandra monitorCassandra = new HardwareCassandra(UUID.randomUUID(), false, 200, "monitor", "DUSTY");
        HardwareCassandra consoleCassandra = new HardwareCassandra(UUID.randomUUID(), false, 200, "console", "AVERAGE");
        HardwareCassandra phoneCassandra = new HardwareCassandra(UUID.randomUUID(), false, 200, "phone", "BAD");

        assertEquals(HardwareTypeConverter.fromRepositoryModel(computerCassandra).getClass(), Computer.class);
        assertEquals(HardwareTypeConverter.fromRepositoryModel(computerCassandra).getCondition(), Condition.FINE);
        assertEquals(HardwareTypeConverter.fromRepositoryModel(monitorCassandra).getClass(), Monitor.class);
        assertEquals(HardwareTypeConverter.fromRepositoryModel(monitorCassandra).getCondition(), Condition.DUSTY);
        assertEquals(HardwareTypeConverter.fromRepositoryModel(consoleCassandra).getClass(), Console.class);
        assertEquals(HardwareTypeConverter.fromRepositoryModel(consoleCassandra).getCondition(), Condition.AVERAGE);
        assertEquals(HardwareTypeConverter.fromRepositoryModel(phoneCassandra).getClass(), Phone.class);
        assertEquals(HardwareTypeConverter.fromRepositoryModel(phoneCassandra).getCondition(), Condition.BAD);
    }
}
