package org.pl.db_repositories;

import org.pl.model.Repair;

public interface RepairRepository {
    Repair getRepairById(int id);

    Repair saveRepair(Repair repair);

    void deleteRepair(Repair repair);
}