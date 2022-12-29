package org.pl.model;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import com.datastax.oss.driver.api.mapper.annotations.PropertyStrategy;

import java.util.Objects;
import java.util.UUID;
import static org.pl.model.Condition.FINE;


@Entity(defaultKeyspace = "repair_hardware")
@CqlName("hardwares")
@PropertyStrategy(mutable = false)
public class Hardware implements EntityInterface {
    @PartitionKey
    @CqlName("hardware_id")
    private UUID id;
    @CqlName("is_archive")
    private boolean archive;
    private int price;
    @CqlName("hardware_type")
    private HardwareType hardwareType;
    private String discriminator;


    public Hardware( int price, HardwareType hardwareType, boolean archive, UUID id, String discriminator) {
        this.id = id;
        this.archive = archive;
        this.price = price;
        this.hardwareType = hardwareType;
        this.discriminator = discriminator;
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

    public String getDiscriminator() {
        return discriminator;
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
