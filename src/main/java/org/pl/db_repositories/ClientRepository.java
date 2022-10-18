package org.pl.db_repositories;

import org.pl.model.Client;

public interface ClientRepository {
    Client getClientById(int id);
    Client saveClient(Client client);
    void deleteClient(Client client);
}
