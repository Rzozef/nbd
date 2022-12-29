package org.pl.model;

import java.util.UUID;

public interface EntityInterface {
    void setArchive(boolean archive);
    boolean isArchive();
    UUID getId();
}
