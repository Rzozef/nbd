package org.pl.model;
import org.pl.exceptions.ClientException;

import java.util.Objects;
import java.util.UUID;

public class Client implements EntityInterface {
    private UUID id;
    private boolean archive;
    private double balance;
    private String firstName;
    private String lastName;
    private String personalId;
    private String phoneNumber;
    private ClientType clientType;
    private Address address;

    public Client(UUID id, double balance, String firstName, String lastName,
                  String personalId, String phoneNumber, ClientType clientType, Address address) {
        this.id = id;
        this.archive = false;
        this.balance = balance;
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalId = personalId;
        this.phoneNumber = phoneNumber;
        this.clientType = clientType;
        this.address = address;
    }

    public double calculateDiscount(int price) throws ClientException {
        return getClientType().calculateDiscount(price);
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void changeBalance(double amount) {
        setBalance(getBalance() + amount);
    }

    @Override
    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    @Override
    public boolean isArchive() {
        return archive;
    }

    @Override
    public UUID getID() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return archive == client.archive && Double.compare(client.balance, balance) == 0 && id.equals(client.id) && firstName.equals(client.firstName) && lastName.equals(client.lastName) && personalId.equals(client.personalId) && phoneNumber.equals(client.phoneNumber) && clientType.equals(client.clientType) && address.equals(client.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, archive, balance, firstName, lastName, personalId, phoneNumber, clientType, address);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", archive=" + archive +
                ", balance=" + balance +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", personalId='" + personalId + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", clientType=" + clientType +
                ", address=" + address +
                '}';
    }
}
