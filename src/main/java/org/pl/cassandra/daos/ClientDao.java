package org.pl.cassandra.daos;

import com.datastax.oss.driver.api.mapper.annotations.*;
import org.pl.cassandra.model.ClientCassandra;
import org.pl.cassandra.queryProviders.ClientQueryProvider;

import java.util.List;
import java.util.UUID;

@Dao
public interface ClientDao {
    @StatementAttributes(consistencyLevel = "QUORUM")
    @Insert(ifNotExists = true)
    boolean create(ClientCassandra client);

    @StatementAttributes(consistencyLevel = "QUORUM")
    @QueryProvider(providerClass = ClientQueryProvider.class)
    ClientCassandra findById(UUID uuid);

    @StatementAttributes(consistencyLevel = "QUORUM")
    @QueryProvider(providerClass = ClientQueryProvider.class)
    List<ClientCassandra> findAll();


    @StatementAttributes(consistencyLevel = "QUORUM")
    @Update
    void update(ClientCassandra client);


    @StatementAttributes(consistencyLevel = "QUORUM")
    @Delete
    void delete(ClientCassandra client);
}
