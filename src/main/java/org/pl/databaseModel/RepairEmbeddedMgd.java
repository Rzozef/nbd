package org.pl.databaseModel;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

public class RepairEmbeddedMgd {
    @BsonCreator
    public RepairEmbeddedMgd(@BsonProperty("_id") int id,
                             @BsonProperty("archive") boolean archive,
                             @BsonProperty("client") ClientMgd client,
                             @BsonProperty("hardware") HardwareMgd hardware) {
        this.id = id;
        this.archive = archive;
        this.client = client;
        this.hardware = hardware;
    }
    @BsonProperty("_id")
    private int id;
    @BsonProperty("archive")
    private boolean archive;
    @BsonProperty("client")
    private ClientMgd client;
    @BsonProperty("hardware")
    private HardwareMgd hardware;

    public int getId() {
        return id;
    }

    public boolean isArchive() {
        return archive;
    }

    public ClientMgd getClient() {
        return client;
    }

    public HardwareMgd getHardware() {
        return hardware;
    }
}
