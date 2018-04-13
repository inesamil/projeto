package pt.isel.ps.gis.model;

import pt.isel.ps.gis.exceptions.ModelException;
import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class StorageId implements Serializable {

    /**
     * COLUNAS
     */
    @Column(name = "house_id", nullable = false)
    private Long houseId;

    @Column(name = "storage_id", nullable = false)
    private Short storageId;

    /**
     * CONSTRUTORES
     */
    protected StorageId() {}

    public StorageId(Long houseId, Short storageId) throws ModelException {
        setHouseId(houseId);
        setStorageId(storageId);
    }

    /**
     * GETTERS E SETTERS
     */
    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) throws ModelException {
        ValidationsUtils.validateHouseId(houseId);
        this.houseId = houseId;
    }

    public Short getStorageId() {
        return storageId;
    }

    public void setStorageId(Short storageId) throws ModelException {
        ValidationsUtils.validateStorageId(storageId);
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
