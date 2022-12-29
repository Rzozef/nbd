package org.pl.cassandra.repositories;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import org.pl.cassandra.daos.HardwareDao;
import org.pl.cassandra.mappers.HardwareMapper;
import org.pl.cassandra.mappers.HardwareMapperBuilder;
import org.pl.cassandra.model.HardwareCassandra;
import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.*;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.UUID;

public class HardwareCassandraRepository implements AutoCloseable {
    private final CqlSession session;
    private final HardwareDao hardwareDao;

    public HardwareCassandraRepository() {
        this.session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress(9042))
                .addContactPoint(new InetSocketAddress(9043))
                .withLocalDatacenter("dc1")
                .withAuthCredentials("cassandra", "cassandrapassword")
                .withKeyspace(CqlIdentifier.fromCql("repair_hardware"))
                .build();
        SimpleStatement createHardwares = SchemaBuilder.createTable(CqlIdentifier.fromCql("hardwares"))
                .ifNotExists()
                .withPartitionKey(CqlIdentifier.fromCql("hardware_id"), DataTypes.UUID)
                .withColumn(CqlIdentifier.fromCql("is_archive"), DataTypes.BOOLEAN)
                .withColumn(CqlIdentifier.fromCql("price"), DataTypes.INT)
                .withColumn(CqlIdentifier.fromCql("hardware_type"), DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql("condition"), DataTypes.TEXT)
                .build();
        session.execute(createHardwares);
        HardwareMapper hardwareMapper = new HardwareMapperBuilder(session).build();
        this.hardwareDao = hardwareMapper.hardwareDao();
    }

    public boolean create(HardwareCassandra hardwareCassandra) {
        return hardwareDao.create(hardwareCassandra);
    }

    public HardwareCassandra findByUId(UUID uuid) {
        return hardwareDao.findByUId(uuid);
    }

    public List<HardwareCassandra> findAll() {
        return hardwareDao.findAll();
    }

    public void update(HardwareCassandra hardwareCassandra) {
        hardwareDao.update(hardwareCassandra);
    }

    public void delete(HardwareCassandra hardwareCassandra) {
        hardwareDao.delete(hardwareCassandra);
    }

    public void deleteAll() {
        SimpleStatement deleteAll = truncate(CqlIdentifier.fromCql("hardwares")).build();
        session.execute(deleteAll);
    }

    @Override
    public void close() {
        session.close();
    }
}
