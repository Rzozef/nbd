package org.pl.databaseModel;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

public class RepairEmbeddedMgd extends AbstractEntityMgd {
    @BsonCreator
    public RepairEmbeddedMgd(@BsonId int entityId,
                             @BsonProperty("archive") boolean archive,
                             @BsonProperty("client") ClientMgd client,
                             @BsonProperty("hardware") HardwareMgd hardware) {
        super(entityId);
        this.archive = archive;
        this.client = client;
        this.hardware = hardware;
    }
    @BsonProperty("archive")
    private boolean archive;
    @BsonProperty("client")
    private ClientMgd client;
    @BsonProperty("hardware")
    private HardwareMgd hardware;

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
