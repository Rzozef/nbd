package org.pl.databaseService;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.pl.converters.ClientAddressConverter;
import org.pl.converters.ClientRedisConverter;
import org.pl.databaseModel.ClientAddressMgd;
import org.pl.databaseModel.ClientRedis;
import org.pl.databaseRepository.ClientMongoRepository;
import org.pl.databaseRepository.ClientRedisRepository;
import org.pl.exceptions.ClientException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.*;

import java.util.*;

public class ClientRedisService {
    private ClientRedisRepository clientRedisRepository;
    private ClientMongoRepository clientMongoRepository;

    public ClientRedisService(ClientRedisRepository clientRedisRepository) {
        this.clientRedisRepository = clientRedisRepository;
        this.clientMongoRepository = new ClientMongoRepository();
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
        clientMongoRepository.add(ClientAddressConverter.toRepositoryModel(client));
        return clientRedisRepository.add(ClientRedisConverter.toRepositoryModel(client));
    }

    public Client getClient(UUID id) throws ClientException, JsonProcessingException {
        if (clientRedisRepository.isConnected()) {
            if (clientRedisRepository.read(id.toString()) == null)
                return null;
            return ClientRedisConverter.fromRepositoryModelClient(clientRedisRepository.read(id.toString()));
        }
        return ClientAddressConverter.fromRepositoryModelClient(clientMongoRepository.findClientByClientId(id));
    }

    public List<Client> getAllClients() throws JsonProcessingException, ClientException {
        if (clientRedisRepository.isConnected()) {
            List<ClientRedis> clientsRedis = clientRedisRepository.readAllClients();
            List<Client> clients = new ArrayList<>();
            for (int i = 0; i < clientsRedis.size(); i++) {
                clients.add(ClientRedisConverter.fromRepositoryModelClient(clientsRedis.get(i)));
            }
            return clients;
        }
        List<ClientAddressMgd> clientsMongo = clientMongoRepository.findAll();
        List<Client> clients = new ArrayList<>();
        for (int i = 0; i < clientsMongo.size(); i++) {
            clients.add(ClientAddressConverter.fromRepositoryModelClient(clientsMongo.get(i)));
        }
        return clients;
    }

    public Client updateClient(UUID id, Client client) throws JsonProcessingException, ClientException {
        clientMongoRepository.updateBalance(id, ClientAddressConverter.toRepositoryModel(client).getBalance());
        clientMongoRepository.updateArchive(id, ClientAddressConverter.toRepositoryModel(client).isArchive());
        clientMongoRepository.updateFirstName(id, ClientAddressConverter.toRepositoryModel(client).getFirstName());
        clientMongoRepository.updateLastName(id, ClientAddressConverter.toRepositoryModel(client).getLastName());
        clientMongoRepository.updatePhoneNumber(id, ClientAddressConverter.toRepositoryModel(client).getPhoneNumber());
        clientMongoRepository.updateCity(id, ClientAddressConverter.toRepositoryModel(client).getCity());
        clientMongoRepository.updateNumber(id, ClientAddressConverter.toRepositoryModel(client).getNumber());
        clientMongoRepository.updateStreet(id, ClientAddressConverter.toRepositoryModel(client).getStreet());
        clientMongoRepository.updateClientType(id, ClientAddressConverter.toRepositoryModel(client).getClientType());
        clientMongoRepository.updateRepairsWithNumberOfRepairs(id, ClientAddressConverter.toRepositoryModel(client).getRepairs());
        return ClientRedisConverter.fromRepositoryModelClient(clientRedisRepository.set(id.toString(), ClientRedisConverter.toRepositoryModel(client)));
    }

    public void deleteClient(UUID id) throws JsonProcessingException {
        clientMongoRepository.remove(id);
        clientRedisRepository.delete(id.toString());
    }

    public void deleteAll() {
        clientMongoRepository.removeAll();
        clientRedisRepository.deleteAll();
    }
}
