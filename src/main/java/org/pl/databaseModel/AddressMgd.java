package org.pl.databaseModel;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.pl.model.ClientType;

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

}
