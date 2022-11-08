package org.pl.databaseModel;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.UUID;

public class RepairEmbeddedMgd extends AbstractEntityMgd {
    @BsonCreator
    public RepairEmbeddedMgd(@BsonId UUID entityId,
                             @BsonProperty("archive") boolean archive,
                             @BsonProperty("client") ClientAddressMgd client,
                             @BsonProperty("hardware") HardwareMgd hardware) {
        super(entityId);
        this.archive = archive;
        this.client = client;
        this.hardware = hardware;
    }
    @BsonProperty("archive")
    private boolean archive;
    @BsonProperty("client")
    private ClientAddressMgd client;
    @BsonProperty("hardware")
    private HardwareMgd hardware;

    public boolean isArchive() {
        return archive;
    }

    public ClientAddressMgd getClient() {
        return client;
    }

    public HardwareMgd getHardware() {
        return hardware;
    }
}
