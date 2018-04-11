package pt.isel.ps.gis.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class StorageId implements Serializable {

    @Id
    @Column(name = "house_id")
    private Long houseId;

    @Id
    @Column(name = "storage_id")
    private Short storageId;

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public Short getStorageId() {
        return storageId;
    }

    public void setStorageId(Short storageId) {
        this.storageId = storageId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        StorageId that = (StorageId) obj;
        return Objects.equals(houseId, that.houseId) &&
                Objects.equals(storageId, that.storageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(houseId, storageId);
    }
}
