//package org.pl.converters;
//
//import org.junit.jupiter.api.Test;
//import org.pl.databaseModel.*;
//import org.pl.exceptions.HardwareException;
//import org.pl.model.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class HardwareTypeConverterTest {
//
//    @Test
//    void convertHardwareTypeToHardwareTypeMgdTest() throws HardwareException {
//        HardwareType computer = new Computer(Condition.FINE);
//        HardwareType phone = new Phone(Condition.FINE);
//        HardwareType console = new Console(Condition.FINE);
//        HardwareType monitor = new Monitor(Condition.FINE);
//        assertEquals(HardwareTypeConverter.toRepositoryModel(computer).getClass(), ComputerMgd.class);
//        assertEquals(HardwareTypeConverter.toRepositoryModel(phone).getClass(), PhoneMgd.class);
//        assertEquals(HardwareTypeConverter.toRepositoryModel(console).getClass(), ConsoleMgd.class);
//        assertEquals(HardwareTypeConverter.toRepositoryModel(monitor).getClass(), MonitorMgd.class);
//    }
//
//    @Test
//    void convertHardwareTypeMgdToHardwareTypeTest() throws HardwareException {
//        HardwareTypeMgd computerMgd = new ComputerMgd(Condition.FINE);
//        HardwareTypeMgd phoneMgd = new PhoneMgd(Condition.FINE);
//        HardwareTypeMgd consoleMgd = new ConsoleMgd(Condition.FINE);
//        HardwareTypeMgd monitorMgd = new MonitorMgd(Condition.FINE);
//        assertEquals(HardwareTypeConverter.fromRepositoryModel(computerMgd).getClass(), Computer.class);
//        assertEquals(HardwareTypeConverter.fromRepositoryModel(phoneMgd).getClass(), Phone.class);
//        assertEquals(HardwareTypeConverter.fromRepositoryModel(consoleMgd).getClass(), Console.class);
//        assertEquals(HardwareTypeConverter.fromRepositoryModel(monitorMgd).getClass(), Monitor.class);
//    }
//}
