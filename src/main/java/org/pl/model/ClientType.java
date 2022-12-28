package org.pl.model;

import org.pl.exceptions.ClientException;

import java.util.Objects;

public abstract class ClientType {
    protected float factor;

    public float getFactor() {
        return factor;
    }

    public int getMaxRepairs() {
        return maxRepairs;
    }

    public void setFactor(float factor) {
        this.factor = factor;
    }

    public void setMaxRepairs(int maxRepairs) {
        this.maxRepairs = maxRepairs;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    protected int maxRepairs;
    protected String typeName;

    public double calculateDiscount(int price) throws ClientException {
        if (price < 0) {
            throw new ClientException(ClientException.CLIENT_TYPE_CALCULATE_DISCOUNT_EXCEPTION);
        }
        return price - (getFactor() * price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientType that = (ClientType) o;
        return Float.compare(that.factor, factor) == 0 && maxRepairs == that.maxRepairs && typeName.equals(that.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(factor, maxRepairs, typeName);
    }
}
