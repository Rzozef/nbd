package org.pl.cassandra;

import com.datastax.oss.driver.api.core.CqlSession;
import org.junit.jupiter.api.Test;
import org.pl.cassandra.daos.HardwareDao;
import org.pl.cassandra.mappers.HardwareMapper;
import org.pl.cassandra.mappers.HardwareMapperBuilder;
import org.pl.model.Computer;
import org.pl.model.Condition;
import org.pl.model.Hardware;

import java.net.InetSocketAddress;
import java.util.UUID;

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
                .build();
    }
    @Test
    void cassandraTest() {
        initConnection();
//        CreateKeyspace keyspace = SchemaBuilder.createKeyspace(CqlIdentifier.fromCql("repair_hardware"))
//                .ifNotExists()
//                .withSimpleStrategy(2)
//                .withDurableWrites(true);
//        SimpleStatement createKeyspace = keyspace.build();
//        session.execute(createKeyspace);
        UUID randomUUID = UUID.randomUUID();
        hardwareMapper = new HardwareMapperBuilder(session).build();
        hardwareDao = hardwareMapper.hardwareDao();
        Computer computer = new Computer(Condition.FINE);
        Hardware hardware = new Hardware(200, computer, false, randomUUID);
        assertTrue(hardwareDao.create(hardware));
        assertEquals(hardwareDao.findByUId(randomUUID), hardware);
        session.close();
    }

}
