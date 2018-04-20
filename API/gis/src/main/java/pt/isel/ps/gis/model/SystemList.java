package pt.isel.ps.gis.model;

import pt.isel.ps.gis.exceptions.EntityException;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "systemlist")
public class SystemList {

    private final static String TYPE = "system";

    /**
     * COLUNAS
     */
    @EmbeddedId
    private SystemListId id;

    /**
     * ASSOCIAÇÕES
     */
    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "house_id", referencedColumnName = "house_id", nullable = false),
            @JoinColumn(name = "list_id", referencedColumnName = "list_id", nullable = false)
    })
    private List list;

    /**
     * CONSTRUTORES
     */
    protected SystemList() {
    }

    public SystemList(Long houseId, String listName) throws EntityException {
        setId(houseId);
        this.list = new List(houseId, listName, TYPE);
    }

    public SystemList(Long houseId, Short listId, String listName) throws EntityException {
        setId(houseId, listId);
        this.list = new List(houseId, listId, listName, TYPE);
    }

    /**
     * GETTERS E SETTERS
     */
    public SystemListId getId() {
        return id;
    }

    public void setId(SystemListId id) {
        this.id = id;
    }

    public void setId(Long houseId) throws EntityException {
        setId(new SystemListId(houseId));
    }

    public void setId(Long houseId, Short listId) throws EntityException {
        setId(new SystemListId(houseId, listId));
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
