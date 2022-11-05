package org.pl.model;

import lombok.*;

import static org.pl.model.Condition.FINE;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Hardware extends AbstractEntity implements Entity {
    private boolean archive;
    private int price;
    private HardwareType hardwareType;

    @Builder
    public Hardware(int entityId, boolean archive, int price, HardwareType hardwareType) {
        super(entityId);
        this.archive = archive;
        this.price = price;
        this.hardwareType = hardwareType;
    }

    public void repair() {
        hardwareType.setCondition(FINE);
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
