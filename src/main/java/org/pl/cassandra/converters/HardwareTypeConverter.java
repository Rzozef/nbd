package org.pl.cassandra.converters;

import org.pl.cassandra.model.HardwareCassandra;
import org.pl.exceptions.HardwareException;
import org.pl.model.*;

public class HardwareTypeConverter {
    public static HardwareType fromRepositoryModel(HardwareCassandra hardwareCassandra) throws HardwareException {
        HardwareType hardwareType;
        switch (hardwareCassandra.getHardwareType()) {
            case "computer" -> {
                hardwareType = new Computer(Condition.valueOf(hardwareCassandra.getCondition()));
                return hardwareType;
            }
            case "console" -> {
                hardwareType = new Console(Condition.valueOf(hardwareCassandra.getCondition()));
                return hardwareType;
            }
            case "monitor" -> {
                hardwareType = new Monitor(Condition.valueOf(hardwareCassandra.getCondition()));
                return hardwareType;
            }
            case "phone" -> {
                hardwareType = new Phone(Condition.valueOf(hardwareCassandra.getCondition()));
                return hardwareType;
            }
            default -> throw new HardwareException(HardwareException.HARDWARE_TYPE_EXCEPTION);
        }
    }
}
