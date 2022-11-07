package org.pl.converters;

import org.pl.databaseModel.*;
import org.pl.exceptions.HardwareException;
import org.pl.model.*;

public class HardwareTypeConverter {
    public static HardwareTypeMgd toRepositoryModel(HardwareType hardwareType) throws HardwareException {
        HardwareTypeMgd hardwareTypeMgd;
        if (hardwareType.getClass() == Computer.class) {
            hardwareTypeMgd = new ComputerMgd(hardwareType.getCondition());
            return hardwareTypeMgd;
        } else if (hardwareType.getClass() == Monitor.class) {
            hardwareTypeMgd = new MonitorMgd(hardwareType.getCondition());
            return hardwareTypeMgd;
        } else if (hardwareType.getClass() == Console.class) {
            hardwareTypeMgd = new ConsoleMgd(hardwareType.getCondition());
            return hardwareTypeMgd;
        } else if (hardwareType.getClass() == Phone.class) {
            hardwareTypeMgd = new PhoneMgd(hardwareType.getCondition());
            return hardwareTypeMgd;
        } else throw new HardwareException(HardwareException.HARDWARE_TYPE_EXCEPTION);
    }

    public static HardwareType fromRepositoryModel(HardwareTypeMgd hardwareTypeMgd) throws HardwareException {
        HardwareType hardwareType;
        if (hardwareTypeMgd.getClass() == ComputerMgd.class) {
            hardwareType = new Computer(hardwareTypeMgd.getCondition());
            return hardwareType;
        } else if (hardwareTypeMgd.getClass() == MonitorMgd.class) {
            hardwareType = new Monitor(hardwareTypeMgd.getCondition());
            return hardwareType;
        } else if (hardwareTypeMgd.getClass() == ConsoleMgd.class) {
            hardwareType = new Console(hardwareTypeMgd.getCondition());
            return hardwareType;
        } else if (hardwareTypeMgd.getClass() == PhoneMgd.class) {
            hardwareType = new Phone(hardwareTypeMgd.getCondition());
            return hardwareType;
        } else throw new HardwareException(HardwareException.HARDWARE_TYPE_EXCEPTION);
    }
}
