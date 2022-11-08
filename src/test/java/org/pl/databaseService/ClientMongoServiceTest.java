package org.pl.databaseService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.databaseRepository.ClientMongoRepository;
import org.pl.exceptions.ClientException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClientMongoServiceTest {
    private ClientMongoService clientMongoService;
    private ClientMongoRepository clientMongoRepository;

    @BeforeEach
    void setUp() {
        clientMongoRepository = new ClientMongoRepository();
        clientMongoService = new ClientMongoService(clientMongoRepository);
    }

    @Test
    void addNegativeTest() {
        assertThrows(ClientException.class,
                ()-> clientMongoService.add("", "Kowalski", "997", "123", "Lodz", "12", "Dabrowskiego"));
        assertThrows(ClientException.class,
                ()-> clientMongoService.add("Jan", "", "997", "123", "Lodz", "12", "Dabrowskiego"));
        assertThrows(ClientException.class,
                ()-> clientMongoService.add("Jan", "Kowalski", "", "123", "Lodz", "12", "Dabrowskiego"));
        assertThrows(ClientException.class,
                ()-> clientMongoService.add("Jan", "Kowalski", "997", "", "Lodz", "12", "Dabrowskiego"));
        assertThrows(ClientException.class,
                ()-> clientMongoService.add("Jan", "Kowalski", "997", "123", "", "12", "Dabrowskiego"));
        assertThrows(ClientException.class,
                ()-> clientMongoService.add("Jan", "Kowalski", "997", "123", "Lodz", "", "Dabrowskiego"));
        assertThrows(ClientException.class,
                ()-> clientMongoService.add("Jan", "Kowalski", "997", "123", "Lodz", "12", ""));
    }

    @AfterEach
    void clearDatabase() {
        clientMongoRepository.removeAll();
    }
}
