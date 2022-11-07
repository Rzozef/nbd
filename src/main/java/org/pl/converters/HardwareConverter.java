package org.pl.converters;

import org.pl.databaseModel.HardwareMgd;
import org.pl.exceptions.HardwareException;
import org.pl.model.Hardware;

public class HardwareConverter {
    public static HardwareMgd toRepositoryModel(Hardware hardware) throws HardwareException {
        return new HardwareMgd(hardware.getEntityId(), hardware.isArchive(), hardware.getPrice(),
                HardwareTypeConverter.toRepositoryModel(hardware.getHardwareType()));
    }

    public static Hardware fromRepositoryModel(HardwareMgd hardwareMgd) throws HardwareException {
        return new Hardware(hardwareMgd.getEntityId(), hardwareMgd.isArchive(), hardwareMgd.getPrice(),
                HardwareTypeConverter.fromRepositoryModel(hardwareMgd.getHardwareType()));
    }
}
