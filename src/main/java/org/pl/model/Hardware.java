package org.pl.model;

import java.util.Objects;
import java.util.UUID;
import static org.pl.model.Condition.FINE;

public class Hardware implements EntityInterface {
    private UUID id;
    private boolean archive;
    private int price;
    private HardwareType hardwareType;

    public Hardware(UUID id, boolean archive, int price, HardwareType hardwareType) {
        this.id = id;
        this.archive = archive;
        this.price = price;
        this.hardwareType = hardwareType;
    }

    public int getPrice() {
        return price;
    }

    public HardwareType getHardwareType() {
        return hardwareType;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void repair() {
        hardwareType.setCondition(FINE);
    }
    @Override
    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    @Override
    public boolean isArchive() {
        return archive;
    }

    @Override
    public UUID getID() {
        return id;
    }

    public void setHardwareType(HardwareType hardwareType) {
        this.hardwareType = hardwareType;
    }

    @Override
    public String toString() {
        return "Hardware{" +
                "id=" + id +
                ", archive=" + archive +
                ", price=" + price +
                ", hardwareType=" + hardwareType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hardware hardware = (Hardware) o;
        return archive == hardware.archive && price == hardware.price && id.equals(hardware.id) && hardwareType.equals(hardware.hardwareType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, archive, price, hardwareType);
    }
}
