package org.pl.databaseModel;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.pl.model.Condition;

@BsonDiscriminator(key = "_clazz", value = "phone")
public class PhoneMgd extends HardwareTypeMgd {
    @BsonCreator
    public PhoneMgd(@BsonProperty("condition") Condition condition) {
        this.condition = condition;
    }
    @BsonProperty("condition")
    private Condition condition;

    public Condition getCondition() {
        return condition;
    }
}
