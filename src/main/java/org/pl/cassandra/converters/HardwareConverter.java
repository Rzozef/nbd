package org.pl.cassandra.converters;

import org.pl.cassandra.model.HardwareCassandra;
import org.pl.exceptions.HardwareException;
import org.pl.model.Hardware;

public class HardwareConverter {
    public static HardwareCassandra toRepositoryModel(Hardware hardware) {
        return new HardwareCassandra(hardware.getId(), hardware.isArchive(), hardware.getPrice(),
                hardware.getDiscriminator(), hardware.getHardwareType().getCondition().name());
    }

    public static Hardware fromRepositoryModel(HardwareCassandra hardwareCassandra) throws HardwareException {
        return new Hardware(hardwareCassandra.getId(), hardwareCassandra.getPrice(),
                HardwareTypeConverter.fromRepositoryModel(hardwareCassandra), hardwareCassandra.isArchive(),
                hardwareCassandra.getHardwareType());
    }
}
