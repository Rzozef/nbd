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

    public boolean isArchive() {
        return archive;
    }

    public int getPrice() {
        return price;
    }

    public String getHardwareType() {
        return hardwareType;
    }

    public String getCondition() {
        return condition;
    }

}
