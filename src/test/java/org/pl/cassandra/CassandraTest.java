package org.pl.cassandra;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspace;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.InetSocketAddress;

public class CassandraTest {
//    private static CqlSession session;
//
//    public void initConnection() {
//        session = CqlSession.builder()
//                .addContactPoint(new InetSocketAddress(9042))
//                .addContactPoint(new InetSocketAddress(9043))
//                .withLocalDatacenter("dc1")
//                .withAuthCredentials("cassandra", "cassandrapassword")
//                .build();
//    }
//    @Test
//    void cassandraTest() {
//        initConnection();
//        CreateKeyspace keyspace = SchemaBuilder.createKeyspace(CqlIdentifier.fromCql("repair_hardware"))
//                .ifNotExists()
//                .withSimpleStrategy(2)
//                .withDurableWrites(true);
//        SimpleStatement createKeyspace = keyspace.build();
//        session.execute(createKeyspace);
//        session.close();
//    }

}
