package org.pl.cassandra.queryProviders;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.mapper.MapperContext;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.insert.Insert;
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

    public List<RepairCassandra> findByClient(UUID uuid) {
        Select selectRepairs = QueryBuilder
                .selectFrom(CqlIdentifier.fromCql("repairs_by_client"))
                .all()
                .where(Relation.column(CqlIdentifier.fromCql("client_id")).isEqualTo(literal(uuid)));
        List<Row> rows = session.execute(selectRepairs.build()).all();
        List<RepairCassandra> repairs = new ArrayList<>();

        rows.forEach(row -> {
            repairs.add(getRepair(row));
        });

        return repairs;
    }

    public List<RepairCassandra> findByHardware(UUID uuid) {
        Select selectRepairs = QueryBuilder
                .selectFrom(CqlIdentifier.fromCql("repairs_by_hardware"))
                .all()
                .where(Relation.column(CqlIdentifier.fromCql("hardware_id")).isEqualTo(literal(uuid)));
        List<Row> rows = session.execute(selectRepairs.build()).all();
        List<RepairCassandra> repairs = new ArrayList<>();

        rows.forEach(row -> {
            repairs.add(getRepair(row));
        });

        return repairs;
    }

    public boolean create(RepairCassandra repair) {
        Insert insert = QueryBuilder.insertInto("repairs")
                .value(CqlIdentifier.fromCql("repair_id"), literal(repair.getId()))
                .value(CqlIdentifier.fromCql("is_archive"), literal(repair.isArchive()))
                .value(CqlIdentifier.fromCql("client_id"), literal(repair.getClient()))
                .value(CqlIdentifier.fromCql("hardware_id"), literal(repair.getHardware()))
                .ifNotExists();

        Insert insert1 = QueryBuilder.insertInto("repairs_by_client")
                .value(CqlIdentifier.fromCql("repair_id"), literal(repair.getId()))
                .value(CqlIdentifier.fromCql("is_archive"), literal(repair.isArchive()))
                .value(CqlIdentifier.fromCql("client_id"), literal(repair.getClient()))
                .value(CqlIdentifier.fromCql("hardware_id"), literal(repair.getHardware()))
                .ifNotExists();

        Insert insert2 = QueryBuilder.insertInto("repairs_by_hardware")
                .value(CqlIdentifier.fromCql("repair_id"), literal(repair.getId()))
                .value(CqlIdentifier.fromCql("is_archive"), literal(repair.isArchive()))
                .value(CqlIdentifier.fromCql("client_id"), literal(repair.getClient()))
                .value(CqlIdentifier.fromCql("hardware_id"), literal(repair.getHardware()))
                .ifNotExists();
        if (findByUId(repair.getId()) == null) {
            session.execute(insert.build());
            session.execute(insert1.build());
            session.execute(insert2.build());
            return true;
        } else {
            return false;
        }
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
