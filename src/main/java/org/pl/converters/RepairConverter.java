package org.pl.converters;

import org.pl.databaseModel.RepairEmbeddedMgd;
import org.pl.exceptions.ClientException;
import org.pl.exceptions.HardwareException;
import org.pl.model.Repair;

public class RepairConverter {
    public static RepairEmbeddedMgd toRepositoryModel(Repair repair) throws HardwareException {
        return new RepairEmbeddedMgd(repair.getEntityId(), repair.isArchive(),
                ClientAddressConverter.toRepositoryModel(repair.getClient()),
                HardwareConverter.toRepositoryModel(repair.getHardware()));
    }

    public static Repair fromRepositoryModel(RepairEmbeddedMgd repairEmbeddedMgd) throws ClientException, HardwareException {
        return new Repair(repairEmbeddedMgd.getEntityId(), repairEmbeddedMgd.isArchive(),
                ClientAddressConverter.fromRepositoryModelClient(repairEmbeddedMgd.getClient()),
                HardwareConverter.fromRepositoryModel(repairEmbeddedMgd.getHardware()));
    }
}
