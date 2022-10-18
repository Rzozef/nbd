package org.pl.model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.*;

public class DatabaseTest {
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
    void testConnectivity() {
        Client client = em.find(Client.class, 1);
        assertThat(client, is(nullValue()));
    }

    @Test
    void insertAndSelectClientTest() {
        ClientType type = new Basic();
        Address address = Address.builder().city("a").number("1").street("b").build();
        Client client = Client.builder().clientType(type).address(address).
                firstName("a").lastName("b").personalId("a").phoneNumber("c").balance(100.0).archive(false).build();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(client);
        transaction.commit();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Client> query = cb.createQuery(Client.class);
        From<Client, Client> from = query.from(Client.class);
        query.select(from).where(cb.equal(from.get(Client_.ID), 1));
        Client found = em.createQuery(query).getSingleResult();
        assertThat(1, is(found.getID()));
        System.out.println(found);
    }
}
