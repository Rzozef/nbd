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
    protected float factor;

    @BsonProperty("maxRepairs")
    protected int maxRepairs;

    @BsonProperty("typeName")
    protected String typeName;
}
