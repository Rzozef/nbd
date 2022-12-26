package org.pl.model;

import lombok.Builder;
import lombok.Data;
import org.pl.exceptions.HardwareException;

import java.util.UUID;

@Data
@Builder
public class Repair implements Entity {
    private UUID id;
    private boolean archive;
    Client client;
    Hardware hardware;

    public double calculateRepairCost() throws HardwareException {
        return getHardware().getHardwareType().calculateRepairCost(getHardware().getPrice());
    }

    @Override
    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    @Override
    public UUID getID() {
        return id;
    }
}
