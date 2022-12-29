package org.pl.cassandra.converters;

import org.junit.jupiter.api.Test;
import org.pl.cassandra.model.ClientCassandra;
import org.pl.model.Address;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class AddressConverterTest {

    @Test
    void convertAddressCassanadraToAddressTest() {
        ClientCassandra clientCassandra = new ClientCassandra(UUID.randomUUID(), false, 300, "Jan", "Kowalski", "12345678901", "123456789", (float)1.0, 3, "basic", "Warsaw", "Zielona", "77");
        assertEquals(AddressConverter.fromRepositoryModel(clientCassandra).getClass(), Address.class);
    }
}
