package org.pl.databaseService;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.pl.converters.ClientAddressConverter;
import org.pl.converters.ClientRedisConverter;
import org.pl.converters.ClientTypeConverter;
import org.pl.databaseModel.AddressMgd;
import org.pl.databaseModel.ClientAddressMgd;
import org.pl.databaseModel.ClientTypeMgd;
import org.pl.databaseRepository.ClientMongoRepository;
import org.pl.databaseRepository.ClientRedisRepository;
import org.pl.exceptions.ClientException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.*;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class ClientMongoService {
    private ClientMongoRepository clientMongoRepository;
    private ClientRedisRepository clientRedisRepository;

    public ClientMongoService(ClientMongoRepository clientMongoRepository) throws JsonProcessingException {
        this.clientMongoRepository = clientMongoRepository;
        this.clientRedisRepository = new ClientRedisRepository();
    }

    public boolean add(String firstName, String lastName, String phoneNumber, String personalId, String city, String number, String street, String typeName) throws RepositoryException, ClientException, JsonProcessingException {
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
        if (clientRedisRepository.isConnected())
            clientRedisRepository.add(ClientRedisConverter.toRepositoryModel(client));
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
        if (clientMongoRepository.findClientByClientId(id) == null)
            return null;
        return ClientAddressConverter.fromRepositoryModelClient(clientMongoRepository.findClientByClientId(id));
    }

    public Address getAddress(UUID id) {
        return ClientAddressConverter.fromRepositoryModelAddress(clientMongoRepository.findAddressByClientId(id));
    }

    public Client updateArchive(UUID id, boolean isArchive) throws ClientException, JsonProcessingException {
        Client client = ClientRedisConverter.fromRepositoryModelClient(clientRedisRepository.read(id.toString()));
        client.setArchive(isArchive);
        clientRedisRepository.set(id.toString(), ClientRedisConverter.toRepositoryModel(client));
        return ClientAddressConverter.fromRepositoryModelClient(clientMongoRepository.updateArchive(id, isArchive));
    }

    public Client updateBalance(UUID id, double newBalance) throws ClientException, JsonProcessingException {
        Client client = ClientRedisConverter.fromRepositoryModelClient(clientRedisRepository.read(id.toString()));
        client.setBalance(newBalance);
        clientRedisRepository.set(id.toString(), ClientRedisConverter.toRepositoryModel(client));
        return ClientAddressConverter.fromRepositoryModelClient(clientMongoRepository.updateBalance(id, newBalance));
    }

    public Client updateFirstName(UUID id, String newFirstName) throws ClientException, JsonProcessingException {
        Client client = ClientRedisConverter.fromRepositoryModelClient(clientRedisRepository.read(id.toString()));
        client.setFirstName(newFirstName);
        clientRedisRepository.set(id.toString(), ClientRedisConverter.toRepositoryModel(client));
        return ClientAddressConverter.fromRepositoryModelClient(clientMongoRepository.updateFirstName(id, newFirstName));
    }

    public Client updateLastName(UUID id, String newLastName) throws ClientException, JsonProcessingException {
        Client client = ClientRedisConverter.fromRepositoryModelClient(clientRedisRepository.read(id.toString()));
        client.setLastName(newLastName);
        clientRedisRepository.set(id.toString(), ClientRedisConverter.toRepositoryModel(client));
        return ClientAddressConverter.fromRepositoryModelClient(clientMongoRepository.updateLastName(id, newLastName));
    }

    public Client updatePhoneNumber(UUID id, String newPhoneNumber) throws ClientException, JsonProcessingException {
        Client client = ClientRedisConverter.fromRepositoryModelClient(clientRedisRepository.read(id.toString()));
        client.setPhoneNumber(newPhoneNumber);
        clientRedisRepository.set(id.toString(), ClientRedisConverter.toRepositoryModel(client));
        return ClientAddressConverter.fromRepositoryModelClient(clientMongoRepository.updatePhoneNumber(id, newPhoneNumber));
    }

    public Client updateClientType(UUID id, ClientTypeMgd newClientType) throws ClientException, JsonProcessingException {
        Client client = ClientRedisConverter.fromRepositoryModelClient(clientRedisRepository.read(id.toString()));
        client.setClientType(ClientTypeConverter.fromRepositoryModel(newClientType));
        clientRedisRepository.set(id.toString(), ClientRedisConverter.toRepositoryModel(client));
        return ClientAddressConverter.fromRepositoryModelClient(clientMongoRepository.updateClientType(id, newClientType));
    }

    public Client updateCity(UUID id, String newCity) throws ClientException, JsonProcessingException {
        Client client = ClientRedisConverter.fromRepositoryModelClient(clientRedisRepository.read(id.toString()));
        client.getAddress().setCity(newCity);
        clientRedisRepository.set(id.toString(), ClientRedisConverter.toRepositoryModel(client));
        return ClientAddressConverter.fromRepositoryModelClient(clientMongoRepository.updateCity(id, newCity));
    }

    public Client updateNumber(UUID id, String newNumber) throws ClientException, JsonProcessingException {
        Client client = ClientRedisConverter.fromRepositoryModelClient(clientRedisRepository.read(id.toString()));
        client.getAddress().setNumber(newNumber);
        clientRedisRepository.set(id.toString(), ClientRedisConverter.toRepositoryModel(client));
        return ClientAddressConverter.fromRepositoryModelClient(clientMongoRepository.updateNumber(id, newNumber));
    }

    public Client updateStreet(UUID id, String newStreet) throws ClientException, JsonProcessingException {
        Client client = ClientRedisConverter.fromRepositoryModelClient(clientRedisRepository.read(id.toString()));
        client.getAddress().setStreet(newStreet);
        clientRedisRepository.set(id.toString(), ClientRedisConverter.toRepositoryModel(client));
        return ClientAddressConverter.fromRepositoryModelClient(clientMongoRepository.updateStreet(id, newStreet));
    }

    public Client updateRepairs(UUID id) throws ClientException, JsonProcessingException, RepositoryException {
        Client client = ClientRedisConverter.fromRepositoryModelClient(clientRedisRepository.read(id.toString()));
        int newRepairs = client.getRepairs() + 1;
        if (newRepairs > client.getClientType().getMaxRepairs())
            throw new RepositoryException(RepositoryException.REPOSITORY_MAX_REPAIRS_EXCEED);
        client.setRepairs(newRepairs);
        clientRedisRepository.set(id.toString(), ClientRedisConverter.toRepositoryModel(client));
        return ClientAddressConverter.fromRepositoryModelClient(clientMongoRepository.updateRepairs(id));
    }

    public Client updateRepairsWithNumberOfRepairs(UUID id, int repairs) throws ClientException, JsonProcessingException {
        Client client = ClientRedisConverter.fromRepositoryModelClient(clientRedisRepository.read(id.toString()));
        client.setRepairs(repairs);
        clientRedisRepository.set(id.toString(), ClientRedisConverter.toRepositoryModel(client));
        return ClientAddressConverter.fromRepositoryModelClient(clientMongoRepository.updateRepairsWithNumberOfRepairs(id, repairs));
    }

    public boolean delete(UUID id) throws JsonProcessingException {
        ClientAddressMgd client = clientMongoRepository.remove(id);
        if (client == null)
            return false;
        clientRedisRepository.delete(id.toString());
        return client.getClass() == ClientAddressMgd.class;
    }
}
