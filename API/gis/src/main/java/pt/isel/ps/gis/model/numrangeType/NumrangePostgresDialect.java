package pt.isel.ps.gis.model.numrangeType;

import org.hibernate.dialect.PostgreSQL95Dialect;

import java.sql.Types;

public class NumrangePostgresDialect extends PostgreSQL95Dialect {

    public NumrangePostgresDialect() {
        super();
        registerColumnType(Types.JAVA_OBJECT, "numrange");
    }
}
