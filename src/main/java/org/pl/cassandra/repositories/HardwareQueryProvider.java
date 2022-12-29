package org.pl.cassandra.repositories;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.mapper.MapperContext;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.relation.Relation;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import org.pl.model.Hardware;
import org.pl.model.HardwareType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;

public class HardwareQueryProvider {
    private final CqlSession session;

    public HardwareQueryProvider(MapperContext ctx) {
        this.session = ctx.getSession();
    }

    Hardware findById(UUID uuid) {
        Select selectHardware = QueryBuilder
                .selectFrom(CqlIdentifier.fromCql("hardwares"))
                .all()
                .where(Relation.column(CqlIdentifier.fromCql("hardware_id")).isEqualTo(literal(uuid)));
        Row row = session.execute(selectHardware.build()).one();
        return getHardware(row);
    }

    List<Hardware> findAll() {
        Select selectHardwares = QueryBuilder
                .selectFrom(CqlIdentifier.fromCql("hardwares"))
                .all();
        List<Row> rows = session.execute(selectHardwares.build()).all();
        List<Hardware> hardwares = new ArrayList<>();

        rows.forEach(row -> {
            hardwares.add(new Hardware(
                    row.getInt("price"),
                    (HardwareType) row.getObject("hardwareType"),
                    row.getBoolean("archive"),
                    row.getUuid("hardware_id")
            ));
        });

        return hardwares;
    }

    private Hardware getHardware(Row row) {
        return new Hardware(
                row.getInt("price"),
                (HardwareType) row.getObject("hardwareType"),
                row.getBoolean("archive"),
                row.getUuid("hardware_id")
        );
    }
}
