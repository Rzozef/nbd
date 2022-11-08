package org.pl.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;

@ToString
@EqualsAndHashCode
public abstract class AbstractEntity {
    private final UUID entityId;

    public UUID getEntityId() {
        return entityId;
    }

    public AbstractEntity(UUID entityId) {
        this.entityId = entityId;
    }
}
