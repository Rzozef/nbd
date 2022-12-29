package org.pl.cassandra;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.core.type.UserDefinedType;
import com.datastax.oss.driver.api.core.type.codec.ExtraTypeCodecs;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.schema.CreateTypeStart;
import com.datastax.oss.driver.internal.core.type.UserDefinedTypeBuilder;
import org.junit.jupiter.api.Test;
import org.pl.cassandra.daos.HardwareDao;
import org.pl.cassandra.mappers.HardwareMapper;
import org.pl.cassandra.mappers.HardwareMapperBuilder;
import org.pl.cassandra.model.HardwareCassandra;
import org.pl.model.Computer;
import org.pl.model.Condition;
import org.pl.model.Hardware;

import javax.xml.crypto.Data;
import java.net.InetSocketAddress;
import java.util.UUID;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.udt;
import static org.junit.jupiter.api.Assertions.*;

public class CassandraTest {
    private static CqlSession session;
    private HardwareMapper hardwareMapper;
    private HardwareDao hardwareDao;

    public void initConnection() {
        session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress(9042))
                .addContactPoint(new InetSocketAddress(9043))
                .withLocalDatacenter("dc1")
                .withAuthCredentials("cassandra", "cassandrapassword")
                .withKeyspace(CqlIdentifier.fromCql("repair_hardware"))
                .addTypeCodecs(
                        ExtraTypeCodecs.enumNamesOf(Condition.class) // text <-> enum
                )
                .build();
    }
    @Test
    void cassandraTest() {
        initConnection();
        SimpleStatement createHardwares = SchemaBuilder.createTable(CqlIdentifier.fromCql("hardwares"))
                .ifNotExists()
                .withPartitionKey(CqlIdentifier.fromCql("hardware_id"), DataTypes.UUID)
                .withColumn(CqlIdentifier.fromCql("is_archive"), DataTypes.BOOLEAN)
                .withColumn(CqlIdentifier.fromCql("price"), DataTypes.INT)
                .withColumn(CqlIdentifier.fromCql("hardware_type"), DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql("condition"), DataTypes.TEXT)
                .build();
        session.execute(createHardwares);
        UUID randomUUID = UUID.randomUUID();
        hardwareMapper = new HardwareMapperBuilder(session).build();
        hardwareDao = hardwareMapper.hardwareDao();
        Computer computer = new Computer(Condition.FINE);
        HardwareCassandra hardware = new HardwareCassandra(randomUUID, false, 100, computer.toString(), "FINE");
        assertTrue(hardwareDao.create(hardware));
        assertEquals(hardwareDao.findByUId(randomUUID).getId(), hardware.getId());
        session.close();
    }

}
