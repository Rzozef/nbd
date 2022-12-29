package org.pl.cassandra.converters;

import org.pl.cassandra.model.RepairCassandra;
import org.pl.cassandra.repositories.ClientCassandraRepository;
import org.pl.cassandra.repositories.HardwareCassandraRepository;
import org.pl.exceptions.ClientException;
import org.pl.exceptions.HardwareException;
import org.pl.model.Repair;

public class RepairConverter {
    private static ClientCassandraRepository clientCassandraRepository;
    private static HardwareCassandraRepository hardwareCassandraRepository;
    public static RepairCassandra toRepositoryModel(Repair repair) {
        return new RepairCassandra(repair.getId(), repair.isArchive(),
                repair.getClient().getId(), repair.getHardware().getId());
    }

    public static Repair fromRepositoryModel(RepairCassandra repairCassandra) throws HardwareException, ClientException {
        return new Repair(repairCassandra.getId(), repairCassandra.isArchive(),
                ClientConverter.fromRepositoryModel(clientCassandraRepository.findByUId(repairCassandra.getClient())),
                HardwareConverter.fromRepositoryModel(hardwareCassandraRepository.findByUId(repairCassandra.getHardware())));
    }
}
