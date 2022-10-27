package org.pl.databaseModel;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

public class HardwareMgd {
    @BsonCreator
    public HardwareMgd(@BsonProperty("_id") int id,
                       @BsonProperty("archive") boolean archive,
                       @BsonProperty("price") int price,
                       @BsonProperty("hardwareType") HardwareTypeMgd hardwareType) {
        this.id = id;
        this.archive = archive;
        this.price = price;
        this.hardwareType = hardwareType;
    }
    @BsonProperty("_id")
    private int id;
    @BsonProperty("archive")
    private boolean archive;
    @BsonProperty("price")
    private int price;
    @BsonProperty("hardwareType")
    private HardwareTypeMgd hardwareType;

    public int getId() {
        return id;
    }

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
