package org.pl.databaseModel;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

public class AddressMgd {
    @BsonCreator
    public AddressMgd(@BsonProperty("city") String city,
                      @BsonProperty("number") String number,
                      @BsonProperty("street") String street) {
        this.city = city;
        this.number = number;
        this.street = street;
    }

    @BsonProperty("city")
    private String city;

    @BsonProperty("number")
    private String number;

    @BsonProperty("street")
    private String street;

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
