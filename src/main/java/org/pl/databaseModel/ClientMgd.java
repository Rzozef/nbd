package org.pl.databaseModel;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.pl.model.ClientType;

public class ClientMgd {
    @BsonCreator
    public ClientMgd(@BsonProperty("archive") boolean archive,
                     @BsonProperty("balance") double balance,
                     @BsonProperty("firstName") String firstName,
                     @BsonProperty("lastName") String lastName,
                     @BsonProperty("_id") int personalId,
                     @BsonProperty("phoneNumber") String phoneNumber,
                     @BsonProperty("clientType") ClientTypeMgd clientType) {
        this.archive = archive;
        this.balance = balance;
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalId = personalId;
        this.phoneNumber = phoneNumber;
        this.clientType = clientType;
    }
    @BsonProperty("archive")
    private boolean archive;

    @BsonProperty("balance")
    private double balance;

    @BsonProperty("firstName")
    private String firstName;

    @BsonProperty("lastName")
    private String lastName;

    @BsonProperty("_id")
    private int personalId;

    @BsonProperty("phoneNumber")
    private String phoneNumber;

    @BsonProperty("clientType")
    private ClientTypeMgd clientType;

    public boolean isArchive() {
        return archive;
    }

    public double getBalance() {
        return balance;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getPersonalId() {
        return personalId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ClientTypeMgd getClientType() {
        return clientType;
    }
}
