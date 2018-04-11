package pt.isel.ps.gis.dal.bdModel.JsonType;

import org.hibernate.dialect.PostgreSQL95Dialect;

import java.sql.Types;

public class PostgreSQL103Dialect extends PostgreSQL95Dialect {

    public PostgreSQL103Dialect() {
        this.registerColumnType(Types.JAVA_OBJECT, "jsonb");
    }
}
