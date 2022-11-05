package org.pl.databaseModel;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

public class ClientMgd extends AbstractEntityMgd {
    @BsonCreator
    public ClientMgd(@BsonId int entityId,
                            @BsonProperty("archive") boolean archive,
                            @BsonProperty("balance") double balance,
                            @BsonProperty("firstName") String firstName,
                            @BsonProperty("lastName") String lastName,
                            @BsonProperty("personalId") String personalId,
                            @BsonProperty("phoneNumber") String phoneNumber,
                            @BsonProperty("clientType") ClientTypeMgd clientType) {
        super(entityId);
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

    @BsonProperty("personalId")
    private String personalId;

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

    public String getPersonalId() {
        return personalId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ClientTypeMgd getClientType() {
        return clientType;
    }
}
