package org.pl.db_repositories;

import org.pl.model.Hardware;

public interface HardwareRepository {
    Hardware getHardwareById(int id);

    Hardware saveHardware(Hardware hardware);

    void deleteHardware(Hardware hardware);
}
