package pt.isel.ps.gis.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "systemlist")
public class SystemList {

    @EmbeddedId
    private SystemListId id;

    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "house_id", referencedColumnName = "house_id", nullable = false),
            @JoinColumn(name = "list_id", referencedColumnName = "list_id", nullable = false)
    })
    private List list;

    public SystemListId getId() {
        return id;
    }

    public void setId(SystemListId id) {
        this.id = id;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SystemList that = (SystemList) obj;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
