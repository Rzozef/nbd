package org.pl.cassandra.daos;

import com.datastax.oss.driver.api.mapper.annotations.*;
import org.pl.cassandra.model.HardwareCassandra;
import org.pl.cassandra.queryProviders.HardwareQueryProvider;


import java.util.List;
import java.util.UUID;

@Dao
public interface HardwareDao {

    @StatementAttributes(consistencyLevel = "QUORUM") //wiekszosc potwierdza => ONE/ALL - alternatywy
    @Insert(ifNotExists = true)
    boolean create(HardwareCassandra hardware);

    @StatementAttributes(consistencyLevel = "QUORUM")
    @QueryProvider(providerClass = HardwareQueryProvider.class)
    HardwareCassandra findByUId(UUID uuid);

    @StatementAttributes(consistencyLevel = "QUORUM")
    @QueryProvider(providerClass = HardwareQueryProvider.class)
    List<HardwareCassandra> findAll();


    @StatementAttributes(consistencyLevel = "QUORUM")
    @Update
    void update(HardwareCassandra hardware);


    @StatementAttributes(consistencyLevel = "QUORUM")
    @Delete
    void delete(HardwareCassandra hardware);
}
