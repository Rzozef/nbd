package org.pl.databaseModel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class ClientRedis {

    @JsonCreator
    public ClientRedis(@JsonProperty("entityId") UUID entityId,
                            @JsonProperty("archive") boolean archive,
                            @JsonProperty("balance") double balance,
                            @JsonProperty("firstName") String firstName,
                            @JsonProperty("lastName") String lastName,
                            @JsonProperty("personalId") String personalId,
                            @JsonProperty("phoneNumber") String phoneNumber,
                            @JsonProperty("clientType") ClientTypeRedis clientType,
                            @JsonProperty("city") String city,
                            @JsonProperty("number") String number,
                            @JsonProperty("street") String street,
                            @JsonProperty("repairs") int repairs) {
        this.entityId = entityId;
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
        this.repairs = repairs;
    }

    public UUID getEntityId() {
        return entityId;
    }

    @JsonProperty
    private UUID entityId;
    @JsonProperty("repairs")
    private int repairs;

    @JsonProperty("archive")
    private boolean archive;

    @JsonProperty("balance")
    private double balance;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("personalId")
    private String personalId;

    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("clientType")
    private ClientTypeRedis clientType;

    @JsonProperty("city")
    private String city;

    @JsonProperty("number")
    private String number;

    @JsonProperty("street")
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

    public String getPersonalId() {
        return personalId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ClientTypeRedis getClientType() {
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

    public int getRepairs() { return repairs; }
}
