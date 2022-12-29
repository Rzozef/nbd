package org.pl.cassandra.model;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import com.datastax.oss.driver.api.mapper.annotations.PropertyStrategy;

import java.util.UUID;

@Entity
@CqlName("repairs")
@PropertyStrategy(mutable = false)
public class RepairCassandra {

    @PartitionKey
    @CqlName("repair_id")
    private UUID id;

    @CqlName("is_archive")
    private boolean archive;

    @CqlName("client_id")
    private UUID client;

    @CqlName("hardware_id")
    private UUID hardware;

    public RepairCassandra(UUID id, boolean archive, UUID client, UUID hardware) {
        this.id = id;
        this.archive = archive;
        this.client = client;
        this.hardware = hardware;
    }

    public UUID getId() {
        return id;
    }

    public boolean isArchive() {
        return archive;
    }

    public UUID getClient() {
        return client;
    }

    public UUID getHardware() {
        return hardware;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }
}
