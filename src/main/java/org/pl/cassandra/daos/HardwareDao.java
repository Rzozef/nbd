package org.pl.cassandra.daos;

import com.datastax.oss.driver.api.mapper.annotations.*;
import org.pl.cassandra.repositories.HardwareQueryProvider;
import org.pl.model.Hardware;

import java.util.List;
import java.util.UUID;

@Dao
public interface HardwareDao {

    @StatementAttributes(consistencyLevel = "QUORUM") //wiekszosc potwierdza => ONE/ALL - alternatywy
    @Insert(ifNotExists = true)
    boolean create(Hardware hardware);

    @StatementAttributes(consistencyLevel = "QUORUM")
    @QueryProvider(providerClass = HardwareQueryProvider.class)
    Hardware findByUId(UUID uuid);

    @StatementAttributes(consistencyLevel = "QUORUM")
    @QueryProvider(providerClass = HardwareQueryProvider.class)
    List<Hardware> findAll();


    @StatementAttributes(consistencyLevel = "QUORUM")
    @Update
    void update(Hardware hardware);


    @StatementAttributes(consistencyLevel = "QUORUM")
    @Delete
    void delete(Hardware hardware);
}
