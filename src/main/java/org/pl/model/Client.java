package org.pl.model;

import lombok.*;
import org.pl.exceptions.ClientException;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Client extends AbstractEntity implements Entity {
    private boolean archive;
    private double balance;
    private String firstName;
    private String lastName;
    private String personalId;
    private String phoneNumber;
    private ClientType clientType;
    private Address address;
    private int repairs;

    @Builder
    public Client(int entityId, boolean archive, double balance, String firstName, String lastName, String personalId, String phoneNumber, ClientType clientType, Address address, int repairs) {
        super(entityId);
        this.archive = archive;
        this.balance = balance;
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalId = personalId;
        this.phoneNumber = phoneNumber;
        this.clientType = clientType;
        this.address = address;
        this.repairs = repairs;
    }

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
        return getEntityId();
    }
}
