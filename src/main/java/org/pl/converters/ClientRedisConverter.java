package org.pl.converters;

import org.pl.databaseModel.ClientRedis;
import org.pl.exceptions.ClientException;
import org.pl.model.Address;
import org.pl.model.Client;

public class ClientRedisConverter {
    public static ClientRedis toRepositoryModel(Client client) {
        return new ClientRedis(client.getEntityId(), client.isArchive(), client.getBalance(), client.getFirstName(),
                client.getLastName(), client.getPersonalId(), client.getPhoneNumber(),
                ClientTypeRedisConverter.toRepositoryModel(client.getClientType()),
                client.getAddress().getCity(), client.getAddress().getNumber(), client.getAddress().getStreet(),
                client.getRepairs());
    }

    public static Client fromRepositoryModelClient(ClientRedis clientRedis) throws ClientException {
        return new Client(clientRedis.getEntityId(), clientRedis.isArchive(), clientRedis.getBalance(),
                clientRedis.getFirstName(), clientRedis.getLastName(), clientRedis.getPersonalId(),
                clientRedis.getPhoneNumber(), ClientTypeRedisConverter.fromRepositoryModel(clientRedis.getClientType()),
                new Address(clientRedis.getCity(), clientRedis.getNumber(), clientRedis.getStreet()),
                clientRedis.getRepairs());
    }
}
