package org.pl.cassandra.services;

import org.pl.cassandra.model.RepairCassandra;
import org.pl.cassandra.repositories.RepairCassandraRepository;

import java.util.List;
import java.util.UUID;

public class RepairCassandraService {
    private RepairCassandraRepository repairCassandraRepository;

    public RepairCassandraService(RepairCassandraRepository repairCassandraRepository) {
        this.repairCassandraRepository = repairCassandraRepository;
    }

    public RepairCassandra create(UUID clientUUID, UUID hardwareUUID) {
        RepairCassandra repairCassandra = new RepairCassandra(UUID.randomUUID(), false, clientUUID, hardwareUUID);
        if (repairCassandraRepository.create(repairCassandra))
            return repairCassandra;
        return null;
    }

    public RepairCassandra findByUId(UUID uuid) {
        return repairCassandraRepository.findByUId(uuid);
    }

    public List<RepairCassandra> findAll() {
        return repairCassandraRepository.findAll();
    }

    public void update(RepairCassandra repairCassandra) {
        repairCassandraRepository.update(repairCassandra);
    }

    public void delete(UUID uuid) {
        RepairCassandra repairCassandra = repairCassandraRepository.findByUId(uuid);
        repairCassandraRepository.delete(repairCassandra);
    }

    public void deleteAll() {
        repairCassandraRepository.deleteAll();
    }
}
