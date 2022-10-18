package org.pl.model;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@MappedSuperclass
public class AbstractEntity implements Serializable {
    @NotNull
    private long entityId;
    @Version
    private long version;
}
