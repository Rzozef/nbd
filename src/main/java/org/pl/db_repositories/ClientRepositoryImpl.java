package org.pl.db_repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.pl.model.Client;

public class ClientRepositoryImpl implements ClientRepository {
    private EntityManager em;

    public ClientRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Client getClientById(int id) {
        return em.find(Client.class, id);
    }

    @Override
    public Client saveClient(Client client) {
        if (client.getID() == 0) {
            em.persist(client);
        } else {
            client = em.merge(client);
        }
        return client;
    }

    @Override
    public void deleteClient(Client client) {
        if (em.contains(client)) {
            em.remove(client);
        } else {
            em.merge(client);
        }
    }
}
