package pt.isel.ps.gis.model;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "systemlist")
public class SystemList {

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
    private Long houseId;

    /**
     * CONSTRUTORES
     */
    protected SystemList() {
    }

    public SystemList(Long houseId, String listName) throws EntityException {
        setList(houseId, listName, "system");
        setPartialId(houseId);
    }

    public SystemList(Long houseId, Short listId, String listName) throws EntityException {
        this.id = new SystemListId(houseId, listId);
        this.list = new List(houseId, listId, listName, "system");
    }

    /**
     * GETTERS E SETTERS
     */
    public SystemListId getId() {
        return id;
    }

    private void setId(SystemListId id) {
        this.id = id;
    }

    public void setPartialId(Long houseId) throws EntityException {
        ValidationsUtils.validateHouseId(houseId);
        this.houseId = houseId;
    }

    public void setId(Long houseId, Short listId) throws EntityException {
        setId(new SystemListId(houseId, listId));
    }

    public List getList() {
        return list;
    }

    private void setList(List list) {
        this.list = list;
    }

    public void setList(Long houseId, String listName, String type) throws EntityException {
        setList(new List(houseId, listName, type));
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
