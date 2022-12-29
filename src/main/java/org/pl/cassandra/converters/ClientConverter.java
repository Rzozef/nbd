package org.pl.cassandra.converters;

import org.pl.cassandra.model.ClientCassandra;
import org.pl.exceptions.ClientException;
import org.pl.model.Client;

public class ClientConverter {
    public static ClientCassandra toRepositoryModel(Client client) {
        return new ClientCassandra(client.getId(), client.isArchive(), client.getBalance(), client.getFirstName(),
                client.getLastName(), client.getPersonalId(), client.getPhoneNumber(),
                client.getClientType().getFactor(), client.getClientType().getMaxRepairs(),
                client.getClientType().getTypeName(), client.getAddress().getCity(),
                client.getAddress().getStreet(), client.getAddress().getNumber());
    }

    public static Client fromRepositoryModel(ClientCassandra clientCassandra) throws ClientException {
        return new Client(clientCassandra.getId(), clientCassandra.getBalance(), clientCassandra.getFirstName(),
                clientCassandra.getLastName(), clientCassandra.getPersonalId(), clientCassandra.getPhoneNumber(),
                ClientTypeConverter.fromRepositoryModel(clientCassandra),
                AddressConverter.fromRepositoryModel(clientCassandra));
    }
}
