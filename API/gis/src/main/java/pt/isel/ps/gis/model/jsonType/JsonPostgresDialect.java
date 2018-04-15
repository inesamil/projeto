package pt.isel.ps.gis.model.jsonType;

import org.hibernate.dialect.PostgreSQL95Dialect;

import java.sql.Types;

public class JsonPostgresDialect extends PostgreSQL95Dialect {

    public JsonPostgresDialect() {
        super();
        registerColumnType(Types.JAVA_OBJECT, "json");
    }
}
