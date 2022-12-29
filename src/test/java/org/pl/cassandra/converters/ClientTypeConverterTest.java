package org.pl.cassandra.converters;

import org.junit.jupiter.api.Test;
import org.pl.cassandra.model.ClientCassandra;
import org.pl.exceptions.ClientException;
import org.pl.model.Basic;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ClientTypeConverterTest {

    @Test
    void convertClientTypeCassandraToClientTypeTest() throws ClientException {
        ClientCassandra clientCassandra = new ClientCassandra(UUID.randomUUID(), false, 300, "Jan", "Kowalski", "12345678901", "123456789", (float)1.0, 3, "Basic", "Warsaw", "Zielona", "77");
        assertEquals(ClientTypeConverter.fromRepositoryModel(clientCassandra).getClass(), Basic.class);
    }
}
