package org.pl.databaseService;

import org.pl.converters.ClientAddressConverter;
import org.pl.converters.HardwareConverter;
import org.pl.converters.RepairConverter;
import org.pl.databaseModel.ClientAddressMgd;
import org.pl.databaseModel.HardwareMgd;
import org.pl.databaseModel.RepairEmbeddedMgd;
import org.pl.databaseRepository.RepairMongoRepository;
import org.pl.exceptions.ClientException;
import org.pl.exceptions.HardwareException;
import org.pl.exceptions.RepairException;
import org.pl.model.Client;
import org.pl.model.Hardware;
import org.pl.model.Repair;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class RepairMongoService {
    private RepairMongoRepository repairMongoRepository;

    public RepairMongoService(RepairMongoRepository repairMongoRepository) {
        this.repairMongoRepository = repairMongoRepository;
    }

    public boolean add(UUID entityId, Client client, Hardware hardware) throws RepairException, ClientException, HardwareException {
        if (Objects.isNull(client))
            throw new RepairException(RepairException.REPAIR_CLIENT_EXCEPTION);
        if (Objects.isNull(hardware))
            throw new RepairException(RepairException.REPAIR_HARDWARE_EXCEPTION);
        Repair repair = Repair.builder()
                .archive(false)
                .client(client)
                .hardware(hardware)
                .entityId(entityId).build();
        if (client.getRepairs() + 1 > client.getClientType().getMaxRepairs()) {
            throw new ClientException(ClientException.CLIENT_MAX_REPAIRS_EXCEEDED);
        }
        return repairMongoRepository.add(RepairConverter.toRepositoryModel(repair));
    }

    public ArrayList<Repair> getAllRepairs() throws HardwareException, ClientException {
        ArrayList<Repair> repairs = new ArrayList<>();
        ArrayList<RepairEmbeddedMgd> repairEmbeddedMgdArrayList = repairMongoRepository.findAll();
        for (RepairEmbeddedMgd repair : repairEmbeddedMgdArrayList) {
            repairs.add(RepairConverter.fromRepositoryModel(repair));
        }
        return repairs;
    }

    public ArrayList<Repair> getAllRepairsByClientId(UUID id) throws HardwareException, ClientException {
        ArrayList<Repair> repairs = new ArrayList<>();
        ArrayList<RepairEmbeddedMgd> repairEmbeddedMgdArrayList = repairMongoRepository.findAllRepairsByClientId(id);
        for (RepairEmbeddedMgd repair : repairEmbeddedMgdArrayList) {
            repairs.add(RepairConverter.fromRepositoryModel(repair));
        }
        return repairs;
    }

    public ArrayList<Hardware> getAllHardwares() throws HardwareException {
        ArrayList<Hardware> hardwares = new ArrayList<>();
        ArrayList<HardwareMgd> hardwareArrayList = repairMongoRepository.findAllHardwares();
        for (HardwareMgd hardware : hardwareArrayList) {
            hardwares.add(HardwareConverter.fromRepositoryModel(hardware));
        }
        return hardwares;
    }

    public ArrayList<Client> getAllClients() throws ClientException {
        ArrayList<Client> clients = new ArrayList<>();
        ArrayList<ClientAddressMgd> clientAddressMgdList = repairMongoRepository.findAllClients();
        for (ClientAddressMgd client : clientAddressMgdList) {
            clients.add(ClientAddressConverter.fromRepositoryModelClient(client));
        }
        return clients;
    }

    public Repair getRepairById(UUID id) throws HardwareException, ClientException {
        return RepairConverter.fromRepositoryModel(repairMongoRepository.find(id));
    }

    public Repair updateArchive(UUID id) throws HardwareException, ClientException {
        return RepairConverter.fromRepositoryModel(repairMongoRepository.updateArchive(id, true));
    }

    public Repair delete(UUID id) throws HardwareException, ClientException {
        return RepairConverter.fromRepositoryModel(repairMongoRepository.remove(id));
    }
}
