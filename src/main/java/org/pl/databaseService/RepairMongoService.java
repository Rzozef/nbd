package org.pl.databaseService;

import org.pl.databaseRepository.RepairMongoRepository;

public class RepairMongoService {
    private RepairMongoRepository repairMongoRepository;

    public RepairMongoService(RepairMongoRepository repairMongoRepository) {
        this.repairMongoRepository = repairMongoRepository;
    }

    public boolean add() {
        return true;
    }
}
