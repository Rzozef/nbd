package org.pl.model;

import lombok.*;
import org.pl.exceptions.HardwareException;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Repair extends AbstractEntity implements Entity {
    private boolean archive;
    Client client;
    Hardware hardware;

    @Builder
    public Repair(int entityId, boolean archive, Client client, Hardware hardware) {
        super(entityId);
        this.archive = archive;
        this.client = client;
        this.hardware = hardware;
    }

    public double calculateRepairCost() throws HardwareException {
        return getHardware().getHardwareType().calculateRepairCost(getHardware().getPrice());
    }

    @Override
    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    @Override
    public int getID() {
        return getEntityId();
    }
}
