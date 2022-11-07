package org.pl.databaseRepository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClientMongoRepositoryTest {
    private ClientMongoRepository clientsCollection;
    @BeforeAll
    void setUp() {
        clientsCollection = new ClientMongoRepository();
        clientsCollection.initConnection();
    }

    @Test
    void addClientPositiveTest() {

    }
}
