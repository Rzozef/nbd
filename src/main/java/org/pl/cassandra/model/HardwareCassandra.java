package org.pl.cassandra.model;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import com.datastax.oss.driver.api.mapper.annotations.PropertyStrategy;

import java.util.UUID;

@Entity
@CqlName("hardwares")
@PropertyStrategy(mutable = false)
public class HardwareCassandra {

    @PartitionKey
    @CqlName("hardware_id")
    private UUID id;

    @CqlName("is_archive")
    private boolean archive;
    private int price;

    @CqlName("hardware_type")
    private String hardwareType;

    private String condition;

    public HardwareCassandra(UUID id, boolean archive, int price, String hardwareType, String condition) {
        this.id = id;
        this.archive = archive;
        this.price = price;
        this.hardwareType = hardwareType;
        this.condition = condition;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getHardwareType() {
        return hardwareType;
    }

    public void setHardwareType(String hardwareType) {
        this.hardwareType = hardwareType;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
