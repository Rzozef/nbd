package org.pl.cassandra.repositories;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import org.pl.cassandra.daos.ClientDao;
import org.pl.cassandra.mappers.ClientMapper;
import org.pl.cassandra.mappers.ClientMapperBuilder;
import org.pl.cassandra.model.ClientCassandra;
import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.*;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.UUID;

public class ClientCassandraRepository implements AutoCloseable {
    private final CqlSession session;
    private final ClientDao clientDao;

    public ClientCassandraRepository() {
        this.session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress(9042))
                .addContactPoint(new InetSocketAddress(9043))
                .withLocalDatacenter("dc1")
                .withAuthCredentials("cassandra", "cassandrapassword")
                .withKeyspace(CqlIdentifier.fromCql("repair_hardware"))
                .build();
        SimpleStatement createClients = SchemaBuilder.createTable(CqlIdentifier.fromCql("clients"))
                .ifNotExists()
                .withPartitionKey(CqlIdentifier.fromCql("client_id"), DataTypes.UUID)
                .withColumn(CqlIdentifier.fromCql("is_archive"), DataTypes.BOOLEAN)
                .withColumn(CqlIdentifier.fromCql("balance"), DataTypes.DOUBLE)
                .withColumn(CqlIdentifier.fromCql("first_name"), DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql("last_name"), DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql("personal_id"), DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql("phone_number"), DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql("factor"), DataTypes.FLOAT)
                .withColumn(CqlIdentifier.fromCql("max_repairs"), DataTypes.INT)
                .withColumn(CqlIdentifier.fromCql("type_name"), DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql("city"), DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql("street"), DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql("number"), DataTypes.TEXT)
                .build();
        session.execute(createClients);
        ClientMapper clientMapper = new ClientMapperBuilder(session).build();
        this.clientDao = clientMapper.clientDao();
    }

    public boolean create(ClientCassandra clientCassandra) {
        return clientDao.create(clientCassandra);
    }

    public ClientCassandra findByUId(UUID uuid) {
        return clientDao.findById(uuid);
    }

    public List<ClientCassandra> findAll() {
        return clientDao.findAll();
    }

    public void update(ClientCassandra clientCassandra) {
        if (clientCassandra != null)
            clientDao.update(clientCassandra);
    }

    public void delete(ClientCassandra clientCassandra) {
        if (clientCassandra != null)
            clientDao.delete(clientCassandra);
    }

    public void deleteAll() {
        SimpleStatement deleteAll = truncate(CqlIdentifier.fromCql("clients")).build();
        session.execute(deleteAll);
    }

    @Override
    public void close() {
        session.close();
    }
}
