package org.pl.databaseModel;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

public class ClientTypeMgd {
    @BsonCreator
    public ClientTypeMgd(@BsonProperty("factor") float factor,
                         @BsonProperty("maxRepairs") int maxRepairs,
                         @BsonProperty("typeName") String typeName) {
        this.factor = factor;
        this.maxRepairs = maxRepairs;
        this.typeName = typeName;
    }

    @BsonProperty("factor")
    private float factor;

    @BsonProperty("maxRepairs")
    private int maxRepairs;

    @BsonProperty("typeName")
    private String typeName;

    public float getFactor() {
        return factor;
    }

    public int getMaxRepairs() {
        return maxRepairs;
    }

    public String getTypeName() {
        return typeName;
    }
}
