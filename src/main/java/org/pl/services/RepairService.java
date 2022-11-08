package org.pl.services;

import org.pl.exceptions.ClientException;
import org.pl.exceptions.HardwareException;
import org.pl.exceptions.RepairException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Client;
import org.pl.model.Hardware;
import org.pl.model.Repair;
import org.pl.repositories.RepairRepository;

import java.util.Objects;
import java.util.UUID;

public class RepairService {
    private final RepairRepository repairRepository;

    public RepairService(RepairRepository repairRepository) {
        this.repairRepository = repairRepository;
    }

    public Repair add(Client client, Hardware hardware) throws RepositoryException, RepairException {
        if (Objects.isNull(client))
            throw new RepairException(RepairException.REPAIR_CLIENT_EXCEPTION);
        if (Objects.isNull(hardware))
            throw new RepairException(RepairException.REPAIR_HARDWARE_EXCEPTION);

        Repair repair = Repair.builder()
                .entityId(UUID.randomUUID())
                .client(client)
                .hardware(hardware)
                .build();
        repairRepository.add(repair);
        return repair;
    }

    public Repair get(UUID id) throws RepositoryException {
        return repairRepository.get(id);
    }

    public int getArchiveSize() throws RepositoryException {
        return repairRepository.getSize(false);
    }

    public String getInfo(UUID id) throws RepositoryException {
        return repairRepository.get(id).toString();
    }

    public int getPresentSize() throws RepositoryException {
        return repairRepository.getSize(true);
    }

    public void repair(UUID id) throws HardwareException, RepositoryException, ClientException {
        repairRepository.repair(id);
    }
}
