package org.pl.databaseModel;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class AbstractEntityRedis {
    @JsonProperty
    private final UUID entityId;


    public UUID getEntityId() {
        return entityId;
    }

    public AbstractEntityRedis(UUID entityId) {
        this.entityId = entityId;
    }
}
