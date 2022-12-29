package org.pl.cassandra.repositories;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.mapper.MapperContext;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.relation.Relation;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import org.pl.cassandra.model.HardwareCassandra;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;

public class HardwareQueryProvider {
    private final CqlSession session;

    public HardwareQueryProvider(MapperContext ctx) {
        this.session = ctx.getSession();
    }

    public HardwareCassandra findByUId(UUID uuid) {
        Select selectHardware = QueryBuilder
                .selectFrom(CqlIdentifier.fromCql("hardwares"))
                .all()
                .where(Relation.column(CqlIdentifier.fromCql("hardware_id")).isEqualTo(literal(uuid)));
        Row row = session.execute(selectHardware.build()).one();
        if (row != null)
            return getHardware(row);
        return null;
    }

    public List<HardwareCassandra> findAll() {
        Select selectHardwares = QueryBuilder
                .selectFrom(CqlIdentifier.fromCql("hardwares"))
                .all();
        List<Row> rows = session.execute(selectHardwares.build()).all();
        List<HardwareCassandra> hardwares = new ArrayList<>();

        rows.forEach(row -> {
            hardwares.add(getHardware(row));
        });

        return hardwares;
    }

    private HardwareCassandra getHardware(Row row) {
        return new HardwareCassandra(
                row.getUuid("hardware_id"),
                row.getBoolean("is_archive"),
                row.getInt("price"),
                row.getString("hardware_type"),
                row.getString("condition")
        );
    }
}
