package org.pl.converters;

import org.pl.databaseModel.AddressMgd;
import org.pl.databaseModel.ClientAddressMgd;
import org.pl.exceptions.ClientException;
import org.pl.model.Address;
import org.pl.model.Client;

public class ClientAddressConverter {
    public static ClientAddressMgd toRepositoryModel(Client client) {
        return new ClientAddressMgd(client.getEntityId(), client.isArchive(), client.getBalance(), client.getFirstName(),
                client.getLastName(), client.getPersonalId(), client.getPhoneNumber(),
                ClientTypeConverter.toRepositoryModel(client.getClientType()), client.getRepairs(),
                client.getAddress().getCity(), client.getAddress().getNumber(), client.getAddress().getStreet());
    }

    public static Address fromRepositoryModelAddress(AddressMgd addressMgd) {
        return new Address(addressMgd.getCity(), addressMgd.getNumber(), addressMgd.getStreet());
    }

    public static Client fromRepositoryModelClient(ClientAddressMgd clientAddressMgd) throws ClientException {
        return new Client(clientAddressMgd.getEntityId(), clientAddressMgd.isArchive(), clientAddressMgd.getBalance(),
                clientAddressMgd.getFirstName(), clientAddressMgd.getLastName(), clientAddressMgd.getPersonalId(),
                clientAddressMgd.getPhoneNumber(), ClientTypeConverter.fromRepositoryModel(clientAddressMgd.getClientType()),
                new Address(clientAddressMgd.getCity(), clientAddressMgd.getNumber(), clientAddressMgd.getStreet()),
                clientAddressMgd.getRepairs());
    }
}
