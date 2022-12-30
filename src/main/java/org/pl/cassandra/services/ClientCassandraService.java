package org.pl.cassandra.services;

import org.pl.cassandra.model.ClientCassandra;
import org.pl.cassandra.repositories.ClientCassandraRepository;

import java.util.List;
import java.util.UUID;

public class ClientCassandraService {
    private ClientCassandraRepository clientCassandraRepository;

    public ClientCassandraService(ClientCassandraRepository clientCassandraRepository) {
        this.clientCassandraRepository = clientCassandraRepository;
    }

    public ClientCassandra create(double balance, String firstName,
                                                  String lastName, String personalId, String phoneNumber, float factor,
                                                  int maxRepairs, String typeName, String city, String street, String number) {
        ClientCassandra clientCassandra = new ClientCassandra(UUID.randomUUID(), false, balance, firstName, lastName,
                personalId, phoneNumber, factor, maxRepairs, typeName, city, street, number);
        if (clientCassandraRepository.create(clientCassandra))
            return clientCassandra;
        return null;
    }

    public ClientCassandra findByUId(UUID uuid) {
        return clientCassandraRepository.findByUId(uuid);
    }

    public List<ClientCassandra> findAll() {
        return clientCassandraRepository.findAll();
    }

    public void update(ClientCassandra clientCassandra) {
        clientCassandraRepository.update(clientCassandra);
    }

    public void delete(UUID uuid) {
        ClientCassandra clientCassandra = clientCassandraRepository.findByUId(uuid);
        clientCassandraRepository.delete(clientCassandra);
    }

    public void deleteAll() {
        clientCassandraRepository.deleteAll();
    }
}
