package org.pl.cassandra.model;


import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import com.datastax.oss.driver.api.mapper.annotations.PropertyStrategy;
import org.pl.model.Address;
import org.pl.model.ClientType;

import java.util.UUID;

@Entity
@CqlName("clients")
@PropertyStrategy(mutable = false)
public class ClientCassandra {

    @PartitionKey
    @CqlName("client_id")
    private UUID id;

    @CqlName("is_archive")
    private boolean archive;
    private double balance;

    @CqlName("first_name")
    private String firstName;

    @CqlName("last_name")
    private String lastName;

    @CqlName("personal_id")
    private String personalId;

    @CqlName("phone_number")
    private String phoneNumber;
    private float factor;

    @CqlName("max_repairs")
    private int maxRepairs;

    @CqlName("type_name")
    private String typeName;
    private String city;
    private String street;
    private String number;

    public ClientCassandra(UUID id, boolean archive, double balance, String firstName, String lastName,
                           String personalId, String phoneNumber, float factor, int maxRepairs, String typeName,
                           String city, String street, String number) {
        this.id = id;
        this.archive = archive;
        this.balance = balance;
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalId = personalId;
        this.phoneNumber = phoneNumber;
        this.factor = factor;
        this.maxRepairs = maxRepairs;
        this.typeName = typeName;
        this.city = city;
        this.street = street;
        this.number = number;
    }

    public UUID getId() {
        return id;
    }

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

    public float getFactor() {
        return factor;
    }

    public int getMaxRepairs() {
        return maxRepairs;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }
}
