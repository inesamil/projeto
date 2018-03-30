package pt.isel.ps.gis.DAL.bdModel;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "SystemList")
public class SystemList extends List {

    private static final String SYSTEM_LIST_TYPE = "system";

    protected SystemList() { }

    public SystemList(ListId id, String name) {
        super(id, name, SYSTEM_LIST_TYPE);
    }
}
