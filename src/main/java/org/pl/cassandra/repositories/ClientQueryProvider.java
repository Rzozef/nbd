package org.pl.cassandra.repositories;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.mapper.MapperContext;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.relation.Relation;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import org.pl.cassandra.model.ClientCassandra;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;

public class ClientQueryProvider {
    private final CqlSession session;

    public ClientQueryProvider(MapperContext ctx) {
        this.session = ctx.getSession();
    }

    public ClientCassandra findById(UUID uuid) {
        Select selectClient = QueryBuilder
                .selectFrom(CqlIdentifier.fromCql("clients"))
                .all()
                .where(Relation.column(CqlIdentifier.fromCql("client_id")).isEqualTo(literal(uuid)));
        Row row = session.execute(selectClient.build()).one();
        if (row != null)
            return getClient(row);
        return null;
    }

    public List<ClientCassandra> findAll() {
        Select selectClients = QueryBuilder
                .selectFrom(CqlIdentifier.fromCql("clients"))
                .all();
        List<Row> rows = session.execute(selectClients.build()).all();
        List<ClientCassandra> clients = new ArrayList<>();

        rows.forEach(row -> {
            clients.add(getClient(row));
        });

        return clients;
    }

    private ClientCassandra getClient(Row row) {
        return new ClientCassandra(
                row.getUuid("client_id"),
                row.getBoolean("is_archive"),
                row.getDouble("balance"),
                row.getString("first_name"),
                row.getString("last_name"),
                row.getString("personal_id"),
                row.getString("phone_number"),
                row.getFloat("factor"),
                row.getInt("max_repairs"),
                row.getString("type_name"),
                row.getString("city"),
                row.getString("street"),
                row.getString("number")
        );
    }
}
