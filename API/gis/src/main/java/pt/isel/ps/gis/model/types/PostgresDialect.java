package pt.isel.ps.gis.model.types;

import org.hibernate.dialect.PostgreSQL95Dialect;

import java.sql.Types;

public class PostgresDialect extends PostgreSQL95Dialect {

    public PostgresDialect() {
        super();
        registerColumnType(Types.JAVA_OBJECT, "json");
        registerColumnType(Types.JAVA_OBJECT, "numrange");
    }
}
