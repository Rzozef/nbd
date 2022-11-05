package org.pl.databaseModel;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.pl.model.Condition;

@BsonDiscriminator(key = "_clazz", value = "computer")
public class ComputerMgd extends HardwareTypeMgd {
    @BsonCreator
    public ComputerMgd(@BsonProperty("condition") Condition condition) {
        this.condition = condition;
    }

    public ComputerMgd() {

    }
    @BsonProperty("condition")
    private Condition condition;

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }
}
