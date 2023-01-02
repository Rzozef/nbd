package org.pl.cassandra.repositories;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import org.pl.cassandra.daos.RepairDao;
import org.pl.cassandra.mappers.RepairMapper;
import org.pl.cassandra.mappers.RepairMapperBuilder;
import org.pl.cassandra.model.RepairCassandra;
import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.*;


import java.net.InetSocketAddress;
import java.util.List;
import java.util.UUID;

public class RepairCassandraRepository implements AutoCloseable {
    private final CqlSession session;
    private final RepairDao repairDao;

    public RepairCassandraRepository() {
        this.session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress(9042))
                .addContactPoint(new InetSocketAddress(9043))
                .withLocalDatacenter("dc1")
                .withAuthCredentials("cassandra", "cassandrapassword")
                .withKeyspace(CqlIdentifier.fromCql("repair_hardware"))
                .build();
        SimpleStatement createRepairs = SchemaBuilder.createTable(CqlIdentifier.fromCql("repairs"))
                .ifNotExists()
                .withPartitionKey(CqlIdentifier.fromCql("repair_id"), DataTypes.UUID)
                .withColumn(CqlIdentifier.fromCql("is_archive"), DataTypes.BOOLEAN)
                .withColumn(CqlIdentifier.fromCql("client_id"), DataTypes.UUID)
                .withColumn(CqlIdentifier.fromCql("hardware_id"), DataTypes.UUID)
                .build();
        SimpleStatement createClientsRepairs = SchemaBuilder.createTable(CqlIdentifier.fromCql("repairs_by_client"))
                .ifNotExists()
                .withPartitionKey(CqlIdentifier.fromCql("client_id"), DataTypes.UUID)
                .withColumn(CqlIdentifier.fromCql("is_archive"), DataTypes.BOOLEAN)
                .withClusteringColumn(CqlIdentifier.fromCql("repair_id"), DataTypes.UUID)
                .withColumn(CqlIdentifier.fromCql("hardware_id"), DataTypes.UUID)
                .build();
        SimpleStatement createHardwaresRepairs = SchemaBuilder.createTable(CqlIdentifier.fromCql("repairs_by_hardware"))
                .ifNotExists()
                .withPartitionKey(CqlIdentifier.fromCql("hardware_id"), DataTypes.UUID)
                .withColumn(CqlIdentifier.fromCql("is_archive"), DataTypes.BOOLEAN)
                .withColumn(CqlIdentifier.fromCql("client_id"), DataTypes.UUID)
                .withClusteringColumn(CqlIdentifier.fromCql("repair_id"), DataTypes.UUID)
                .build();
        session.execute(createRepairs);
        session.execute(createClientsRepairs);
        session.execute(createHardwaresRepairs);
        RepairMapper repairMapper = new RepairMapperBuilder(session).build();
        this.repairDao = repairMapper.repairDao();
    }

    public boolean create(RepairCassandra repairCassandra) {
        return repairDao.create(repairCassandra);
    }

    public RepairCassandra findByUId(UUID uuid) {
        return repairDao.findByUId(uuid);
    }

    public List<RepairCassandra> findByClient(UUID uuid) { return repairDao.findByClient(uuid); }

    public List<RepairCassandra> findByHardware(UUID uuid) { return repairDao.findByHardware(uuid); }

    public List<RepairCassandra> findAll() {
        return repairDao.findAll();
    }

    public void update(RepairCassandra repairCassandra) {
        repairDao.update(repairCassandra);
    }

    public void delete(RepairCassandra repairCassandra) {
        repairDao.delete(repairCassandra);
    }

    public void deleteAll() {
        SimpleStatement deleteAll = truncate(CqlIdentifier.fromCql("repairs")).build();
        SimpleStatement deleteAll1 = truncate(CqlIdentifier.fromCql("repairs_by_client")).build();
        SimpleStatement deleteAll2 = truncate(CqlIdentifier.fromCql("repairs_by_hardware")).build();
        session.execute(deleteAll);
        session.execute(deleteAll1);
        session.execute(deleteAll2);
    }

    @Override
    public void close() {
        session.close();
    }
}
