package org.pl.model;

import java.util.Objects;

public class Address {
    private String city;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return city.equals(address.city) && number.equals(address.number) && street.equals(address.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, number, street);
    }

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", number='" + number + '\'' +
                ", street='" + street + '\'' +
                '}';
    }

    public Address(String city, String number, String street) {
        this.city = city;
        this.number = number;
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    private String number;
    private String street;
}
