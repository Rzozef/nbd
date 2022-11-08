package org.pl.databaseService;

import org.pl.converters.ClientAddressConverter;
import org.pl.converters.ClientTypeConverter;
import org.pl.databaseModel.AddressMgd;
import org.pl.databaseModel.ClientAddressMgd;
import org.pl.databaseModel.ClientTypeMgd;
import org.pl.databaseRepository.ClientMongoRepository;
import org.pl.exceptions.ClientException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Address;
import org.pl.model.Client;
import org.pl.model.ClientType;

import java.util.ArrayList;
import java.util.Objects;

public class ClientMongoService {
    private ClientMongoRepository clientMongoRepository;

    public ClientMongoService(ClientMongoRepository clientMongoRepository) {
        this.clientMongoRepository = clientMongoRepository;
    }

    public boolean add(String firstName, String lastName, String phoneNumber, String personalId, String city, String number, String street) throws RepositoryException, ClientException {
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
        Address address = Address.builder()
                .city(city)
                .number(number)
                .street(street)
                .build();
        Client client = Client.builder()
                .personalId(personalId)
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(phoneNumber)
                .repairs(0)
                .address(address)
                .entityId(clientMongoRepository.getNumberOfDocuments())
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

    public Client getClient(int id) throws ClientException {
        return ClientAddressConverter.fromRepositoryModelClient(clientMongoRepository.findClientByClientId(id));
    }

    public Address getAddress(int id) {
        return ClientAddressConverter.fromRepositoryModelAddress(clientMongoRepository.findAddressByClientId(id));
    }

    public ClientType getClientType(int id) throws ClientException {
        return ClientTypeConverter.fromRepositoryModel(clientMongoRepository.findClientTypeByClientId(id));
    }

    public Client updateArchive(int id, boolean isArchive) throws ClientException {
        return ClientAddressConverter.fromRepositoryModelClient(clientMongoRepository.updateArchive(id, isArchive));
    }

    public Client updateBalance(int id, double newBalance) throws ClientException {
        return ClientAddressConverter.fromRepositoryModelClient(clientMongoRepository.updateBalance(id, newBalance));
    }

    public Client updateFirstName(int id, String newFirstName) throws ClientException {
        return ClientAddressConverter.fromRepositoryModelClient(clientMongoRepository.updateFirstName(id, newFirstName));
    }

    public Client updateLastName(int id, String newLastName) throws ClientException {
        return ClientAddressConverter.fromRepositoryModelClient(clientMongoRepository.updateLastName(id, newLastName));
    }

    public Client updatePhoneNumber(int id, String newPhoneNumber) throws ClientException {
        return ClientAddressConverter.fromRepositoryModelClient(clientMongoRepository.updatePhoneNumber(id, newPhoneNumber));
    }

    public Client updateClientType(int id, ClientTypeMgd newClientType) throws ClientException {
        return ClientAddressConverter.fromRepositoryModelClient(clientMongoRepository.updateClientType(id, newClientType));
    }

    public Client updateCity(int id, String newCity) throws ClientException {
        return ClientAddressConverter.fromRepositoryModelClient(clientMongoRepository.updateCity(id, newCity));
    }

    public Client updateNumber(int id, String newNumber) throws ClientException {
        return ClientAddressConverter.fromRepositoryModelClient(clientMongoRepository.updateNumber(id, newNumber));
    }

    public Client updateStreet(int id, String newStreet) throws ClientException {
        return ClientAddressConverter.fromRepositoryModelClient(clientMongoRepository.updateStreet(id, newStreet));
    }

    public Client updateRepairs(int id) throws ClientException {
        return ClientAddressConverter.fromRepositoryModelClient(clientMongoRepository.updateRepairs(id));
    }

    public boolean delete(int id) {
        return clientMongoRepository.remove(id).getClass() == ClientAddressMgd.class;
    }
}
