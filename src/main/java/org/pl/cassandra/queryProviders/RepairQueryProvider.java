package org.pl.cassandra.queryProviders;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.mapper.MapperContext;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.relation.Relation;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import org.pl.cassandra.model.RepairCassandra;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;

public class RepairQueryProvider {
    private final CqlSession session;

    public RepairQueryProvider(MapperContext ctx) {
        this.session = ctx.getSession();
    }

    public RepairCassandra findByUId(UUID uuid) {
        Select selectRepair = QueryBuilder
                .selectFrom(CqlIdentifier.fromCql("repairs"))
                .all()
                .where(Relation.column(CqlIdentifier.fromCql("repair_id")).isEqualTo(literal(uuid)));
        Row row = session.execute(selectRepair.build()).one();
        if (row != null)
            return getRepair(row);
        return null;
    }

    public List<RepairCassandra> findAll() {
        Select selectRepairs = QueryBuilder
                .selectFrom(CqlIdentifier.fromCql("repairs"))
                .all();
        List<Row> rows = session.execute(selectRepairs.build()).all();
        List<RepairCassandra> repairs = new ArrayList<>();

        rows.forEach(row -> {
            repairs.add(getRepair(row));
        });

        return repairs;
    }

    private RepairCassandra getRepair(Row row) {
        return new RepairCassandra(
                row.getUuid("repair_id"),
                row.getBoolean("is_archive"),
                row.getUuid("client_id"),
                row.getUuid("hardware_id")
        );
    }
}
