package pt.isel.ps.gis.models;

import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ListId implements Serializable {

    /**
     * COLUNAS
     */
    @Id
    @Column(name = "house_id", nullable = false)
    private Long houseId;

    @Id
    @Column(name = "list_id", nullable = false)
    private Short listId;

    /**
     * CONSTRUTORES
     */
    protected ListId() {}

    public ListId(Long houseId, Short listId) throws IllegalArgumentException {
        setHouseId(houseId);
        setListId(listId);
    }

    /**
     * GETTERS E SETTERS
     */
    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) throws IllegalArgumentException{
        ValidationsUtils.validateHouseId(houseId);
        this.houseId = houseId;
    }

    public Short getListId() {
        return listId;
    }

    public void setListId(Short listId) throws IllegalArgumentException {
        ValidationsUtils.validateListId(listId);
        this.listId = listId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ListId that = (ListId) obj;
        return Objects.equals(houseId, that.houseId) &&
                Objects.equals(listId, that.listId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(houseId, listId);
    }
}
