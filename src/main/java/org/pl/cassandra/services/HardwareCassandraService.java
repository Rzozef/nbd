package org.pl.cassandra.services;

import org.pl.cassandra.model.HardwareCassandra;
import org.pl.cassandra.repositories.HardwareCassandraRepository;

import java.util.List;
import java.util.UUID;

public class HardwareCassandraService {
    private HardwareCassandraRepository hardwareCassandraRepository;

    public HardwareCassandraService(HardwareCassandraRepository hardwareCassandraRepository) {
        this.hardwareCassandraRepository = hardwareCassandraRepository;
    }

    public HardwareCassandra create(int price, String hardwareType, String condition) {
        HardwareCassandra hardwareCassandra = new HardwareCassandra(UUID.randomUUID(), false, price, hardwareType, condition);
        if (hardwareCassandraRepository.create(hardwareCassandra))
            return hardwareCassandra;
        return null;
    }

    public HardwareCassandra findByUId(UUID uuid) {
        return hardwareCassandraRepository.findByUId(uuid);
    }

    public List<HardwareCassandra> findAll() {
        return hardwareCassandraRepository.findAll();
    }

    public void update(HardwareCassandra hardwareCassandra) {
        hardwareCassandraRepository.update(hardwareCassandra);
    }

    public void delete(UUID uuid) {
        HardwareCassandra hardwareCassandra = hardwareCassandraRepository.findByUId(uuid);
        hardwareCassandraRepository.delete(hardwareCassandra);
    }

    public void deleteAll() {
        hardwareCassandraRepository.deleteAll();
    }
}
