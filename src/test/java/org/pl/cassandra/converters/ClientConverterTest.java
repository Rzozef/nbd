package org.pl.cassandra.converters;

import org.junit.jupiter.api.Test;
import org.pl.cassandra.model.ClientCassandra;
import org.pl.exceptions.ClientException;
import org.pl.model.Address;
import org.pl.model.Basic;
import org.pl.model.Client;
import org.pl.model.ClientType;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ClientConverterTest {

    @Test
    void convertClientToClientCassanadraTest() {
        Address address = new Address("Warsaw", "77", "Zielona");
        ClientType clientType = new Basic();
        Client client = new Client(UUID.randomUUID(), 300, "Jan", "Kowalski", "12345678901", "123456789", clientType, address);
        assertEquals(ClientConverter.toRepositoryModel(client).getClass(), ClientCassandra.class);
        assertEquals(ClientConverter.toRepositoryModel(client).getCity(), address.getCity());
        assertEquals(ClientConverter.toRepositoryModel(client).getFactor(), clientType.getFactor());
    }

    @Test
    void convertClientCassandraToClientTest() throws ClientException {
        ClientCassandra clientCassandra = new ClientCassandra(UUID.randomUUID(), false, 300, "Jan", "Kowalski", "12345678901", "123456789", (float)1.0, 3, "Basic", "Warsaw", "Zielona", "77");
        assertEquals(ClientConverter.fromRepositoryModel(clientCassandra).getClass(), Client.class);
        assertEquals(ClientConverter.fromRepositoryModel(clientCassandra).getAddress().getClass(), Address.class);
        assertEquals(ClientConverter.fromRepositoryModel(clientCassandra).getAddress().getCity(), "Warsaw");
        assertEquals(ClientConverter.fromRepositoryModel(clientCassandra).getClientType().getClass(), Basic.class);
        assertEquals(ClientConverter.fromRepositoryModel(clientCassandra).getClientType().getMaxRepairs(), 2);

    }
}
