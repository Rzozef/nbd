package org.pl.databaseService;

import org.pl.converters.ClientAddressConverter;
import org.pl.converters.HardwareConverter;
import org.pl.databaseModel.ClientAddressMgd;
import org.pl.databaseModel.HardwareMgd;
import org.pl.databaseRepository.HardwareMongoRepository;
import org.pl.exceptions.HardwareException;
import org.pl.exceptions.ServiceException;
import org.pl.model.*;

import java.util.ArrayList;
import java.util.UUID;

public class HardwareMongoService {
    private HardwareMongoRepository hardwareMongoRepository;

    public HardwareMongoService(HardwareMongoRepository hardwareMongoRepository) {
        this.hardwareMongoRepository = hardwareMongoRepository;
    }

    public boolean add(boolean archive, int price, String condition, String hardwareType) throws HardwareException, ServiceException {
        if (price <= 0)
            throw new HardwareException(HardwareException.HARDWARE_PRICE_EXCEPTION);
        condition = condition.toLowerCase();
        Condition c = switch (condition) {
            case "unrepairable" -> Condition.UNREPAIRABLE;
            case "very_bad" -> Condition.VERY_BAD;
            case "bad" -> Condition.BAD;
            case "average" -> Condition.AVERAGE;
            case "dusty" -> Condition.DUSTY;
            case "fine" -> Condition.FINE;
            default -> throw new ServiceException(ServiceException.HARDWARE_SERVICE_INVALID_CONDITION_EXCEPTION);
        };
        HardwareType type;
        hardwareType = hardwareType.toLowerCase();
        switch (hardwareType) {
            case "computer" -> type = Computer.builder()
                    .condition(c)
                    .build();
            case "console" -> type = Console.builder()
                    .condition(c)
                    .build();
            case "monitor" -> type = Monitor.builder()
                    .condition(c)
                    .build();
            case "phone" -> type = Phone.builder()
                    .condition(c)
                    .build();
            default -> throw new ServiceException(ServiceException.HARDWARE_SERVICE_INVALID_HARDWARE_EXCEPTION);
        }
        Hardware hardware = Hardware.builder()
                .entityId(UUID.randomUUID())
                .archive(archive)
                .price(price)
                .hardwareType(type).build();
        return hardwareMongoRepository.add(HardwareConverter.toRepositoryModel(hardware));
    }

    public ArrayList<Hardware> getAllHardwares() throws HardwareException {
        ArrayList<Hardware> hardwares = new ArrayList<>();
        ArrayList<HardwareMgd> hardwareMgdList = hardwareMongoRepository.findAll();
        for (HardwareMgd hardwareMgd : hardwareMgdList) {
            hardwares.add(HardwareConverter.fromRepositoryModel(hardwareMgd));
        }
        return hardwares;
    }

    public Hardware getHardware(int id) throws HardwareException {
        return HardwareConverter.fromRepositoryModel(hardwareMongoRepository.find(id));
    }

    public Hardware updateArchive(int id, boolean archive) throws HardwareException {
        return HardwareConverter.fromRepositoryModel(hardwareMongoRepository.updateArchive(id, archive));
    }

    public Hardware updatePrice(int id, int price) throws HardwareException {
        return HardwareConverter.fromRepositoryModel(hardwareMongoRepository.updatePrice(id, price));
    }

    public boolean delete(int id) {
        return hardwareMongoRepository.remove(id).getClass() == HardwareMgd.class;
    }
}
