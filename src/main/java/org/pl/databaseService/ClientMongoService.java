package org.pl.databaseService;

import org.pl.converters.ClientAddressConverter;
import org.pl.databaseModel.AddressMgd;
import org.pl.databaseModel.ClientAddressMgd;
import org.pl.databaseModel.ClientTypeMgd;
import org.pl.databaseRepository.ClientMongoRepository;
import org.pl.exceptions.ClientException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.*;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class ClientMongoService {
    private ClientMongoRepository clientMongoRepository;

    public ClientMongoService(ClientMongoRepository clientMongoRepository) {
        this.clientMongoRepository = clientMongoRepository;
    }

    public boolean add(String firstName, String lastName, String phoneNumber, String personalId, String city, String number, String street, String typeName) throws RepositoryException, ClientException {
        if (Objects.equals(firstName, ""))
            throw new ClientException(ClientException.CLIENT_FIRST_NAME_EXCEPTION);
        if (Objects.equals(lastName, ""))
            throw new ClientException(ClientException.CLIENT_LAST_NAME_EXCEPTION);
        if (Objects.equals(phoneNumber, ""))
            throw new ClientException(ClientException.CLIENT_PHONE_NUMBER_EXCEPTION);
        if (Objects.equals(personalId, ""))
            throw new ClientException(ClientException.CLIENT_PERSONALID_EXCEPTION);
        if (Objects.equals(city, ""))
            throw new ClientException(ClientException.CLIENT_ADDRESS_EXCEPTION);
        if (Objects.equals(number, ""))
            throw new ClientException(ClientException.CLIENT_ADDRESS_EXCEPTION);
        if (Objects.equals(street, ""))
            throw new ClientException(ClientException.CLIENT_ADDRESS_EXCEPTION);
        if (Objects.equals(typeName, ""))
            throw new ClientException(ClientException.CLIENT_TYPE_EXCEPTION);
        ClientType clientType;
        switch (typeName.toLowerCase(Locale.ROOT)) {
            case "basic" -> clientType = new Basic();
            case "vip" -> clientType = new Vip();
            case "premium" -> clientType = new Premium();
            default -> throw new ClientException(ClientException.CLIENT_TYPE_EXCEPTION);
        }
        Address address = Address.builder()
                .city(city)
                .number(number)
                .street(street)
                .build();
        Client client = Client.builder()
                .personalId(personalId)
                .firstName(firstName)
                .lastName(lastName)
                .archive(false)
                .phoneNumber(phoneNumber)
                .repairs(0)
                .address(address)
                .entityId(UUID.randomUUID())
                .clientType(clientType)
                .build();
        return clientMongoRepository.add(ClientAddressConverter.toRepositoryModel(client));
    }
    public ArrayList<Client> getAllClients() throws ClientException {
        ArrayList<Client> clients = new ArrayList<>();
        ArrayList<ClientAddressMgd> clientAddressMgdList = clientMongoRepository.findAll();
        for (ClientAddressMgd client : clientAddressMgdList) {
            clients.add(ClientAddressConverter.fromRepositoryModelClient(client));
        }
        return clients;
    }

    public ArrayList<Address> getAllAddresses() {
        ArrayList<Address> addresses = new ArrayList<>();
        ArrayList<AddressMgd> addressesMgd = clientMongoRepository.findAllAddresses();
        for (AddressMgd addressMgd : addressesMgd) {
            addresses.add(ClientAddressConverter.fromRepositoryModelAddress(addressMgd));
        }
        return addresses;
    }

    public Client getClient(UUID id) throws ClientException {
        return ClientAddressConverter.fromRepositoryModelClient(clientMongoRepository.findClientByClientId(id));
    }

    public Address getAddress(UUID id) {
        return ClientAddressConverter.fromRepositoryModelAddress(clientMongoRepository.findAddressByClientId(id));
    }

    public Client updateArchive(UUID id, boolean isArchive) throws ClientException {
        return ClientAddressConverter.fromRepositoryModelClient(clientMongoRepository.updateArchive(id, isArchive));
    }

    public Client updateBalance(UUID id, double newBalance) throws ClientException {
        return ClientAddressConverter.fromRepositoryModelClient(clientMongoRepository.updateBalance(id, newBalance));
    }

    public Client updateFirstName(UUID id, String newFirstName) throws ClientException {
        return ClientAddressConverter.fromRepositoryModelClient(clientMongoRepository.updateFirstName(id, newFirstName));
    }

    public Client updateLastName(UUID id, String newLastName) throws ClientException {
        return ClientAddressConverter.fromRepositoryModelClient(clientMongoRepository.updateLastName(id, newLastName));
    }

    public Client updatePhoneNumber(UUID id, String newPhoneNumber) throws ClientException {
        return ClientAddressConverter.fromRepositoryModelClient(clientMongoRepository.updatePhoneNumber(id, newPhoneNumber));
    }

    public Client updateClientType(UUID id, ClientTypeMgd newClientType) throws ClientException {
        return ClientAddressConverter.fromRepositoryModelClient(clientMongoRepository.updateClientType(id, newClientType));
    }

    public Client updateCity(UUID id, String newCity) throws ClientException {
        return ClientAddressConverter.fromRepositoryModelClient(clientMongoRepository.updateCity(id, newCity));
    }

    public Client updateNumber(UUID id, String newNumber) throws ClientException {
        return ClientAddressConverter.fromRepositoryModelClient(clientMongoRepository.updateNumber(id, newNumber));
    }

    public Client updateStreet(UUID id, String newStreet) throws ClientException {
        return ClientAddressConverter.fromRepositoryModelClient(clientMongoRepository.updateStreet(id, newStreet));
    }

    public Client updateRepairs(UUID id) throws ClientException {
        return ClientAddressConverter.fromRepositoryModelClient(clientMongoRepository.updateRepairs(id));
    }

    public boolean delete(UUID id) {
        ClientAddressMgd client = clientMongoRepository.remove(id);
        if (client == null)
            return false;
        else return client.getClass() == ClientAddressMgd.class;
    }
}
