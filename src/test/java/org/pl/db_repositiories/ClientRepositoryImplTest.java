package org.pl.db_repositiories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.pl.db_repositories.ClientRepositoryImpl;
import org.pl.model.Address;
import org.pl.model.Basic;
import org.pl.model.Client;
import org.pl.model.ClientType;

public class ClientRepositoryImplTest {
    private static EntityManagerFactory emf;
    private static EntityManager em;

    @BeforeAll
    static void beforeAll() {
        emf = Persistence.createEntityManagerFactory("POSTGRES_REPAIR_PU");
        em = emf.createEntityManager();
    }

    @AfterAll
    static void afterAll() {
        if (emf != null) {
            emf.close();
        }
    }

    @Test
    void saveClientTest() {
        ClientRepositoryImpl repository = new ClientRepositoryImpl(em);
        ClientType type = new Basic();
        Address address = Address.builder().city("a").number("1").street("b").build();
        Client client = Client.builder().clientType(type).address(address).
                firstName("a").lastName("b").personalId("a").phoneNumber("c").balance(100.0).archive(false).build();
        repository.saveClient(client);
        Assertions.assertTrue(em.contains(client));
        client.setBalance(50.0);
        repository.saveClient(client);
        Assertions.assertEquals(50.0, repository.getClientById(client.getID()).getBalance());
    }

    @Test
    void deleteClientTest() {
        ClientType type = new Basic();
        Address address = Address.builder().city("a").number("1").street("b").build();
        Client client = Client.builder().clientType(type).address(address).
                firstName("a").lastName("b").personalId("a").phoneNumber("c").balance(100.0).archive(false).build();
        Client client2 = Client.builder().clientType(type).address(address).
                firstName("a").lastName("b").personalId("a").phoneNumber("d").balance(150.0).archive(false).build();
        ClientRepositoryImpl repository = new ClientRepositoryImpl(em);
        repository.saveClient(client);
        repository.saveClient(client2);
        Assertions.assertTrue(em.contains(client) && em.contains((client2)));
        repository.deleteClient(client2);
        Assertions.assertFalse(em.contains(client2));
    }

    @Test
    void getClientByIdTest() {
        ClientType type = new Basic();
        Address address = Address.builder().city("a").number("1").street("b").build();
        Client client = Client.builder().clientType(type).address(address).
                firstName("a").lastName("b").personalId("a").phoneNumber("c").balance(100.0).archive(false).build();
        Client client2 = Client.builder().clientType(type).address(address).
                firstName("a").lastName("b").personalId("a").phoneNumber("d").balance(150.0).archive(false).build();
        ClientRepositoryImpl repository = new ClientRepositoryImpl(em);
        repository.saveClient(client);
        repository.saveClient(client2);
        Assertions.assertEquals(client, repository.getClientById(client.getID()));
        Assertions.assertNotEquals(client2, repository.getClientById(client.getID()));
    }
}
