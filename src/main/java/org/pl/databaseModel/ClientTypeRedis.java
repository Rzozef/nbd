package org.pl.databaseModel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientTypeRedis {
    @JsonCreator
    public ClientTypeRedis(@JsonProperty("factor") float factor,
                         @JsonProperty("maxRepairs") int maxRepairs,
                         @JsonProperty("typeName") String typeName) {
        this.factor = factor;
        this.maxRepairs = maxRepairs;
        this.typeName = typeName;
    }

    @JsonProperty("factor")
    private float factor;

    @JsonProperty("maxRepairs")
    private int maxRepairs;

    @JsonProperty("typeName")
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
