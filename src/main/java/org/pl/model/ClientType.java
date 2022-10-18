package org.pl.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.pl.exceptions.ClientException;

@Data
@Entity
@Access(AccessType.FIELD)
public abstract class ClientType {
    @NotNull
    protected float factor;
    @NotNull
    protected int maxRepairs;
    @NotNull
    protected String typeName;
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int id;

    public double calculateDiscount(int price) throws ClientException {
        if (price < 0) {
            throw new ClientException(ClientException.CLIENT_TYPE_CALCULATE_DISCOUNT_EXCEPTION);
        }
        return price - (getFactor() * price);
    }
}
