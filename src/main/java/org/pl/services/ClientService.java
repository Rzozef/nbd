package org.pl.services;

import org.pl.exceptions.ClientException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Address;
import org.pl.model.Basic;
import org.pl.model.Client;
import org.pl.repositories.ClientRepository;

import java.util.Objects;
import java.util.UUID;

public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client add(String firstName, String lastName, String personalId, String phoneNumber, Address address) throws RepositoryException, ClientException {
        if (Objects.equals(firstName, ""))
            throw new ClientException(ClientException.CLIENT_FIRST_NAME_EXCEPTION);
        if (Objects.equals(lastName, ""))
            throw new ClientException(ClientException.CLIENT_LAST_NAME_EXCEPTION);
        if (Objects.equals(personalId, ""))
            throw new ClientException(ClientException.CLIENT_PERSONAL_ID_EXCEPTION);
        if (Objects.equals(phoneNumber, ""))
            throw new ClientException(ClientException.CLIENT_PHONE_NUMBER_EXCEPTION);
        if (Objects.isNull(address))
            throw new ClientException(ClientException.CLIENT_ADDRESS_EXCEPTION);
        Client client = new Client(UUID.randomUUID(), 0, firstName, lastName, personalId, phoneNumber,
                new Basic(), address);
        clientRepository.add(client);
        return client;
    }

    public Client add(String firstName, String lastName, String personalId, String phoneNumber, String city, String number, String street) throws RepositoryException {
        Address address = new Address(city, number, street);
        Client client = new Client(UUID.randomUUID(), 0, firstName, lastName, personalId,
                phoneNumber, new Basic(), address);
        clientRepository.add(client);
        return client;
    }

    public Client get(UUID id) throws RepositoryException {
        return clientRepository.get(id);
    }

    public int getArchiveSize() throws RepositoryException {
        return clientRepository.getSize(false);
    }

    public double getClientBalance(UUID id) throws RepositoryException {
        return clientRepository.get(id).getBalance();
    }

    public int getPresentSize() throws RepositoryException {
        return clientRepository.getSize(true);
    }

    public String getInfo(UUID id) throws RepositoryException {
        return clientRepository.get(id).toString();
    }

    public void remove(UUID id) throws RepositoryException {
        clientRepository.archivise(id);
    }
}
