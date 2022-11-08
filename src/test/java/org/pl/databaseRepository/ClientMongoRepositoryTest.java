package org.pl.databaseRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.databaseModel.AddressMgd;
import org.pl.databaseModel.ClientAddressMgd;
import org.pl.databaseModel.ClientTypeMgd;
import org.pl.exceptions.ClientException;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ClientMongoRepositoryTest {
    private ClientMongoRepository clientMongoRepository;
    private ClientTypeMgd clientTypeMgd;
    private ClientAddressMgd clientAddressMgd;
    private ClientAddressMgd clientAddressMgd2;
    @BeforeEach
    void setUp() {
        clientTypeMgd = new ClientTypeMgd(1.0f, 2, "Basic");
        clientAddressMgd = new ClientAddressMgd(UUID.randomUUID(), false, 200, "Jan", "Slawko", "12345678901", "123456789", clientTypeMgd, 0, "city", "5", "street");
        clientAddressMgd2 = new ClientAddressMgd(UUID.randomUUID(), false, 200, "Janek", "Slawko", "12345678901", "123456789", clientTypeMgd, 0, "city", "5", "street");
        clientMongoRepository = new ClientMongoRepository();
        clientMongoRepository.initConnection();
    }
    @Test
    void addClientPositiveTest() {
        assertTrue(clientMongoRepository.add(clientAddressMgd));
    }

    @Test
    void addClientNegativeTest() {
        assertTrue(clientMongoRepository.add(clientAddressMgd));
        assertFalse(clientMongoRepository.add(clientAddressMgd));
    }

    @Test
    void findAllTest() {
        clientMongoRepository.add(clientAddressMgd);
        clientMongoRepository.add(clientAddressMgd2);
        assertEquals(2, clientMongoRepository.findAll().size());
    }

    @Test
    void findAllAddressesTest() {
        clientMongoRepository.add(clientAddressMgd);
        clientMongoRepository.add(clientAddressMgd2);
        ArrayList<AddressMgd> addressMgds = clientMongoRepository.findAllAddresses();
        assertEquals(2, addressMgds.size());
        assertEquals(addressMgds.get(0).getClass(), AddressMgd.class);
    }

    @Test
    void findClientByClientIdClientIsInDatabaseTest() {
        clientMongoRepository.add(clientAddressMgd);
        assertEquals(clientAddressMgd.getEntityId(), clientMongoRepository.findClientByClientId(clientAddressMgd.getEntityId()).getEntityId());
    }

    @Test
    void findClientByClientIdClientIsNotInDatabaseTest() {
        assertNull(clientMongoRepository.findClientByClientId(clientAddressMgd.getEntityId()));
    }

    @Test
    void findAddressByClientIdClientIsInDatabaseTest() {
        clientMongoRepository.add(clientAddressMgd);
        assertEquals(clientMongoRepository.findAddressByClientId(clientAddressMgd.getEntityId()).getCity(), "city");
    }

    @Test
    void findAddressByClientIdClientIsNotInDatabaseTest() {
        assertNull(clientMongoRepository.findAddressByClientId(clientAddressMgd.getEntityId()));
    }

    @Test
    void removeClientByClientId() {
        clientMongoRepository.add(clientAddressMgd);
        assertNotNull(clientMongoRepository.findClientByClientId(clientAddressMgd.getEntityId()));
        clientMongoRepository.remove(clientAddressMgd.getEntityId());
        assertNull(clientMongoRepository.findClientByClientId(clientAddressMgd.getEntityId()));
    }

    @Test
    void removeAllTest() {
        clientMongoRepository.add(clientAddressMgd);
        clientMongoRepository.add(clientAddressMgd2);
        assertEquals(2, clientMongoRepository.findAll().size());
        clientMongoRepository.removeAll();
        assertEquals(0, clientMongoRepository.findAll().size());
    }

    @Test
    void updateArchiveTest() {
        clientMongoRepository.add(clientAddressMgd);
        assertFalse(clientMongoRepository.findClientByClientId(clientAddressMgd.getEntityId()).isArchive());
        clientMongoRepository.updateArchive(clientAddressMgd.getEntityId(), true);
        assertTrue(clientMongoRepository.findClientByClientId(clientAddressMgd.getEntityId()).isArchive());
    }

    @Test
    void updateBalanceTest() {
        clientMongoRepository.add(clientAddressMgd);
        assertEquals(200, clientMongoRepository.findClientByClientId(clientAddressMgd.getEntityId()).getBalance());
        clientMongoRepository.updateBalance(clientAddressMgd.getEntityId(), 100);
        assertEquals(100, clientMongoRepository.findClientByClientId(clientAddressMgd.getEntityId()).getBalance());
    }

    @Test
    void updateFirstNameTest() {
        clientMongoRepository.add(clientAddressMgd);
        assertEquals("Jan", clientMongoRepository.findClientByClientId(clientAddressMgd.getEntityId()).getFirstName());
        clientMongoRepository.updateFirstName(clientAddressMgd.getEntityId(), "Kacper");
        assertEquals("Kacper", clientMongoRepository.findClientByClientId(clientAddressMgd.getEntityId()).getFirstName());
    }

    @Test
    void updateLastNameTest() {
        clientMongoRepository.add(clientAddressMgd);
        assertEquals("Slawko", clientMongoRepository.findClientByClientId(clientAddressMgd.getEntityId()).getLastName());
        clientMongoRepository.updateLastName(clientAddressMgd.getEntityId(), "Kowalski");
        assertEquals("Kowalski", clientMongoRepository.findClientByClientId(clientAddressMgd.getEntityId()).getLastName());
    }

    @Test
    void updatePhoneNumberTest() {
        clientMongoRepository.add(clientAddressMgd);
        assertEquals("123456789", clientMongoRepository.findClientByClientId(clientAddressMgd.getEntityId()).getPhoneNumber());
        clientMongoRepository.updatePhoneNumber(clientAddressMgd.getEntityId(), "987654321");
        assertEquals("987654321", clientMongoRepository.findClientByClientId(clientAddressMgd.getEntityId()).getPhoneNumber());
    }

    @Test
    void updateClientTypeTest() {
        ClientTypeMgd newClientTypeMgd = new ClientTypeMgd(0.8f, 5, "Premium");
        clientMongoRepository.add(clientAddressMgd);
        assertEquals(clientTypeMgd.getTypeName(), clientMongoRepository.findClientByClientId(clientAddressMgd.getEntityId()).getClientType().getTypeName());
        clientMongoRepository.updateClientType(clientAddressMgd.getEntityId(), newClientTypeMgd);
        assertEquals(newClientTypeMgd.getTypeName(), clientMongoRepository.findClientByClientId(clientAddressMgd.getEntityId()).getClientType().getTypeName());
    }

    @Test
    void updateCityTest() {
        clientMongoRepository.add(clientAddressMgd);
        assertEquals("city", clientMongoRepository.findClientByClientId(clientAddressMgd.getEntityId()).getCity());
        clientMongoRepository.updateCity(clientAddressMgd.getEntityId(), "miasto");
        assertEquals("miasto", clientMongoRepository.findClientByClientId(clientAddressMgd.getEntityId()).getCity());
    }

    @Test
    void updateNumberTest() {
        clientMongoRepository.add(clientAddressMgd);
        assertEquals("5", clientMongoRepository.findClientByClientId(clientAddressMgd.getEntityId()).getNumber());
        clientMongoRepository.updateNumber(clientAddressMgd.getEntityId(), "2");
        assertEquals("2", clientMongoRepository.findClientByClientId(clientAddressMgd.getEntityId()).getNumber());
    }

    @Test
    void updateStreetTest() {
        clientMongoRepository.add(clientAddressMgd);
        assertEquals("street", clientMongoRepository.findClientByClientId(clientAddressMgd.getEntityId()).getStreet());
        clientMongoRepository.updateStreet(clientAddressMgd.getEntityId(), "ulica");
        assertEquals("ulica", clientMongoRepository.findClientByClientId(clientAddressMgd.getEntityId()).getStreet());
    }

    @Test
    void updateRepairsTest() throws ClientException {
        clientMongoRepository.add(clientAddressMgd);
        assertEquals(0, clientMongoRepository.findClientByClientId(clientAddressMgd.getEntityId()).getRepairs());
        clientMongoRepository.updateRepairs(clientAddressMgd.getEntityId());
        assertEquals(1, clientMongoRepository.findClientByClientId(clientAddressMgd.getEntityId()).getRepairs());
    }

    @AfterEach
    void clearDatabase() {
        clientMongoRepository.removeAll();
    }
}
