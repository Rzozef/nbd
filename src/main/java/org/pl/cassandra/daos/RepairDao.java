package org.pl.cassandra.daos;

import com.datastax.oss.driver.api.mapper.annotations.*;
import org.pl.cassandra.model.HardwareCassandra;
import org.pl.cassandra.model.RepairCassandra;
import org.pl.cassandra.queryProviders.HardwareQueryProvider;
import org.pl.cassandra.queryProviders.RepairQueryProvider;

import java.util.List;
import java.util.UUID;

public interface RepairDao {
    @StatementAttributes(consistencyLevel = "QUORUM")
    @Insert(ifNotExists = true)
    boolean create(RepairCassandra repair);

    @StatementAttributes(consistencyLevel = "QUORUM")
    @QueryProvider(providerClass = RepairQueryProvider.class)
    RepairCassandra findByUId(UUID uuid);

    @StatementAttributes(consistencyLevel = "QUORUM")
    @QueryProvider(providerClass = RepairQueryProvider.class)
    List<RepairCassandra> findAll();

    @StatementAttributes(consistencyLevel = "QUORUM")
    @Update
    void update(RepairCassandra repair);

    @StatementAttributes(consistencyLevel = "QUORUM")
    @Delete
    void delete(RepairCassandra repair);
}
