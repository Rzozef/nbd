package org.pl.databaseModel;

import org.bson.codecs.pojo.annotations.BsonId;
import java.io.Serializable;

public abstract class AbstractEntityMgd implements Serializable {
    @BsonId
    private final int entityId;

    public int getEntityId() {
        return entityId;
    }

    public AbstractEntityMgd(int entityId) {
        this.entityId = entityId;
    }
}
