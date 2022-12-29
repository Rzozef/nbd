package org.pl.cassandra.converters;

import org.pl.cassandra.model.ClientCassandra;
import org.pl.model.Address;

public class AddressConverter {
    public static Address fromRepositoryModel(ClientCassandra clientCassandra) {
        return new Address(clientCassandra.getCity(), clientCassandra.getNumber(), clientCassandra.getStreet());
    }
}
