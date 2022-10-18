package org.pl.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pl.exceptions.ClientException;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@jakarta.persistence.Entity
@Access(AccessType.FIELD)
public class Client extends AbstractEntity implements Entity, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull
    private boolean archive;
    @NotNull
    private double balance;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    @AttributeOverride(name = "id", column = @Column(name = "pesel", unique = true))
    private String personalId;
    @NotNull
    private String phoneNumber;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @NotNull
    private ClientType clientType;
    @Embedded
    @NotNull
    private Address address;

    public double calculateDiscount(int price) throws ClientException {
        return getClientType().calculateDiscount(price);
    }

    public void changeBalance(double amount) {
        setBalance(getBalance() + amount);
    }

    @Override
    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    @Override
    public int getID() {
        return id;
    }
}
