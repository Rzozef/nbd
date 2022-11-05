package org.pl.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public abstract class AbstractEntity {
    private final int entityId;

    public int getEntityId() {
        return entityId;
    }

    public AbstractEntity(int entityId) {
        this.entityId = entityId;
    }
}
