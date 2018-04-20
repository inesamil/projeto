package pt.isel.ps.gis.model;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SystemListId implements Serializable {

    @Column(name = "house_id", nullable = false)
    private Long houseId;

    @Column(name = "list_id", nullable = false)
    private Short listId;

    public SystemListId(Long houseId) throws EntityException {
        setHouseId(houseId);
    }

    public SystemListId(Long houseId, Short listId) throws EntityException {
        this(houseId);
        setListId(listId);
    }

    /**
     * GETTERS E SETTERS
     */
    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) throws EntityException {
        ValidationsUtils.validateHouseId(houseId);
        this.houseId = houseId;
    }

    public Short getListId() {
        return listId;
    }

    public void setListId(Short listId) throws EntityException {
        ValidationsUtils.validateListId(listId);
        this.listId = listId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SystemListId that = (SystemListId) obj;
        return Objects.equals(houseId, that.houseId) &&
                Objects.equals(listId, that.listId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(houseId, listId);
    }
}
