package org.pl.databaseModel;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.pl.model.Condition;

@BsonDiscriminator(key="_clazz")
public abstract class HardwareTypeMgd {
    @BsonIgnore
    private Condition condition;
}
