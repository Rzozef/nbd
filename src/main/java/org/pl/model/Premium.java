package org.pl.model;

public class Premium extends ClientType {
    public Premium() {
        setFactor(0.9f);
        setMaxRepairs(5);
        setTypeName("Premium");
    }
}
