package pt.isel.ps.gis.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserListId implements Serializable {

    @Id
    @Column(name = "house_id", nullable = false)
    private Long houseId;

    @Id
    @Column(name = "list_id", nullable = false)
    private Short listId;

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public Short getListId() {
        return listId;
    }

    public void setListId(Short listId) {
        this.listId = listId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UserListId that = (UserListId) obj;
        return Objects.equals(houseId, that.houseId) &&
                Objects.equals(listId, that.listId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(houseId, listId);
    }
}