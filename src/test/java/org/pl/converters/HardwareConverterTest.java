package org.pl.converters;

import org.junit.jupiter.api.Test;
import org.pl.databaseModel.ComputerMgd;
import org.pl.databaseModel.HardwareMgd;
import org.pl.databaseModel.HardwareTypeMgd;
import org.pl.exceptions.HardwareException;
import org.pl.model.Computer;
import org.pl.model.Condition;
import org.pl.model.Hardware;
import org.pl.model.HardwareType;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class HardwareConverterTest {
    @Test
    void convertHardwareToHardwareMgdTest() throws HardwareException {
        HardwareType computer = new Computer(Condition.AVERAGE);
        Hardware hardware = new Hardware(UUID.randomUUID(), false, 200, computer);
        assertEquals(HardwareConverter.toRepositoryModel(hardware).getClass(), HardwareMgd.class);
    }

    @Test
    void convertHardwareMgdToHardwareTest() throws HardwareException {
        HardwareTypeMgd computerMgd = new ComputerMgd(Condition.AVERAGE);
        HardwareMgd hardwareMgd = new HardwareMgd(UUID.randomUUID(), false, 200, computerMgd);
        assertEquals(HardwareConverter.fromRepositoryModel(hardwareMgd).getClass(), Hardware.class);
    }
}
