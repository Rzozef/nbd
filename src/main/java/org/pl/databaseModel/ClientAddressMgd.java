package org.pl.databaseModel;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.pl.model.ClientType;

public class ClientAddressMgd {
    @BsonCreator
    public ClientAddressMgd(@BsonProperty("archive") boolean archive,
                            @BsonProperty("balance") double balance,
                            @BsonProperty("firstName") String firstName,
                            @BsonProperty("lastName") String lastName,
                            @BsonProperty("personalId") int personalId,
                            @BsonProperty("phoneNumber") String phoneNumber,
                            @BsonProperty("clientType") ClientTypeMgd clientType,
                            @BsonProperty("city") String city,
                            @BsonProperty("number") String number,
                            @BsonProperty("street") String street) {
        this.archive = archive;
        this.balance = balance;
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalId = personalId;
        this.phoneNumber = phoneNumber;
        this.clientType = clientType;
        this.city = city;
        this.number = number;
        this.street = street;
    }

    @BsonProperty("archive")
    private boolean archive;

    @BsonProperty("balance")
    private double balance;

    @BsonProperty("firstName")
    private String firstName;

    @BsonProperty("lastName")
    private String lastName;

    @BsonProperty("personalId")
    private int personalId;

    @BsonProperty("phoneNumber")
    private String phoneNumber;

    @BsonProperty("clientType")
    private ClientTypeMgd clientType;

    @BsonProperty("city")
    private String city;

    @BsonProperty("number")
    private String number;

    @BsonProperty("street")
    private String street;

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

    public String getCity() {
        return city;
    }

    public String getNumber() {
        return number;
    }

    public String getStreet() {
        return street;
    }
}
