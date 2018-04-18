package pt.isel.ps.gis.model;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.model.types.NumrangeUserType;
import pt.isel.ps.gis.utils.RestrictionsUtils;
import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "storage")
@TypeDef(name = "NumrangeUserType", typeClass = NumrangeUserType.class)
public class Storage {

    /**
     * COLUNAS
     */
    @EmbeddedId
    private StorageId id;

    @Basic
    @Column(name = "storage_name", length = RestrictionsUtils.STORAGE_NAME_MAX_LENGTH, nullable = false)
    private String storageName;

    @Basic
    @Column(name = "storage_temperature", nullable = false)
    @Type(type = "NumrangeUserType")
    private Numrange storageTemperature;

    /**
     * ASSOCIAÇÕES
     */
    @OneToMany(mappedBy = "storage")
    private Collection<StockItemMovement> stockitemmovements;

    @OneToMany(mappedBy = "storage")
    private Collection<StockItemStorage> stockitemstorages;

    @ManyToOne
    @JoinColumn(name = "house_id", referencedColumnName = "house_id", nullable = false, insertable = false, updatable = false)
    private House houseByHouseId;

    /**
     * CONSTRUTORES
     */
    protected Storage() {
    }

    private Storage(String storageName, Numrange storageTemperature) throws EntityException {
        setStorageName(storageName);
        setStorageTemperature(storageTemperature);
    }

    public Storage(Long houseId, String storageName, Numrange storageTemperature) throws EntityException {
        this(storageName, storageTemperature);
        setId(houseId);
    }

    public Storage(Long houseId, Short storageId, String storageName, Numrange storageTemperature) throws EntityException {
        this(storageName, storageTemperature);
        setId(houseId, storageId);
    }

    /**
     * GETTERS E SETTERS
     */
    public StorageId getId() {
        return id;
    }

    public void setId(StorageId id) {
        this.id = id;
    }

    private void setId(Long houseId) throws EntityException {
        setId(new StorageId(houseId));
    }

    private void setId(Long houseId, Short storageId) throws EntityException {
        setId(new StorageId(houseId, storageId));
    }

    public String getStorageName() {
        return storageName;
    }

    public void setStorageName(String storageName) throws EntityException {
        ValidationsUtils.validateStorageName(storageName);
        this.storageName = storageName;
    }

    public Numrange getStorageTemperature() {
        return storageTemperature;
    }

    public void setStorageTemperature(Numrange storageTemperature) throws EntityException {
        ValidationsUtils.validateStorageTemperature(storageTemperature);
        this.storageTemperature = storageTemperature;
    }

    public Collection<StockItemMovement> getStockitemmovements() {
        return stockitemmovements;
    }

    public void setStockitemmovements(Collection<StockItemMovement> stockitemmovements) {
        this.stockitemmovements = stockitemmovements;
    }

    public Collection<StockItemStorage> getStockitemstorages() {
        return stockitemstorages;
    }

    public void setStockitemstorages(Collection<StockItemStorage> stockitemstorages) {
        this.stockitemstorages = stockitemstorages;
    }

    public House getHouseByHouseId() {
        return houseByHouseId;
    }

    public void setHouseByHouseId(House houseByHouseId) {
        this.houseByHouseId = houseByHouseId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Storage that = (Storage) obj;
        return Objects.equals(id, that.id) &&
                Objects.equals(storageName, that.storageName) &&
                Objects.equals(storageTemperature, that.storageTemperature);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, storageName, storageTemperature);
    }
}
