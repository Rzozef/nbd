package org.pl.databaseModel;

import org.bson.codecs.pojo.annotations.BsonId;
import java.io.Serializable;
import java.util.UUID;

public abstract class AbstractEntityMgd implements Serializable {
    @BsonId
    private final UUID entityId;

    public UUID getEntityId() {
        return entityId;
    }

    public AbstractEntityMgd(UUID entityId) {
        this.entityId = entityId;
    }
}
