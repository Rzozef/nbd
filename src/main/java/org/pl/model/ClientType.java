package org.pl.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.pl.exceptions.ClientException;

@Data
@Entity
public abstract class ClientType {
    protected float factor;
    protected int maxRepairs;
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
