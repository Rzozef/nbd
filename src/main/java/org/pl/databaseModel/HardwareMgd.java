package org.pl.databaseModel;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.UUID;

public class HardwareMgd extends AbstractEntityMgd{
    @BsonCreator
    public HardwareMgd(@BsonId UUID entityId,
                       @BsonProperty("archive") boolean archive,
                       @BsonProperty("price") int price,
                       @BsonProperty("hardwareType") HardwareTypeMgd hardwareType) {
        super(entityId);
        this.archive = archive;
        this.price = price;
        this.hardwareType = hardwareType;
    }
    @BsonProperty("archive")
    private boolean archive;
    @BsonProperty("price")
    private int price;
    @BsonProperty("hardwareType")
    private HardwareTypeMgd hardwareType;

    public boolean isArchive() {
        return archive;
    }

    public int getPrice() {
        return price;
    }

    public HardwareTypeMgd getHardwareType() {
        return hardwareType;
    }
}
