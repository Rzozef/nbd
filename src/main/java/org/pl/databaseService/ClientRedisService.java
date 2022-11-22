package org.pl.databaseService;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.pl.converters.ClientAddressConverter;
import org.pl.converters.ClientRedisConverter;
import org.pl.databaseModel.ClientAddressMgd;
import org.pl.databaseModel.ClientRedis;
import org.pl.databaseRepository.ClientRedisRepository;
import org.pl.exceptions.ClientException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.*;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class ClientRedisService {
    private ClientRedisRepository clientRedisRepository;

    public ClientRedisService(ClientRedisRepository clientRedisRepository) {
        this.clientRedisRepository = clientRedisRepository;
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
        return clientRedisRepository.add(ClientRedisConverter.toRepositoryModel(client));
    }

    public Client getClient(UUID id) throws ClientException, JsonProcessingException {
        return ClientRedisConverter.fromRepositoryModelClient(clientRedisRepository.read(id.toString()));
    }

    public Client updateClient(UUID id, Client client) throws JsonProcessingException, ClientException {
        return ClientRedisConverter.fromRepositoryModelClient(clientRedisRepository.set(id.toString(), ClientRedisConverter.toRepositoryModel(client)));
    }

    public void deleteClient(UUID id) throws JsonProcessingException {
        clientRedisRepository.delete(id.toString());
    }

    public void deleteAll() {
        clientRedisRepository.deleteAll();
    }
}
