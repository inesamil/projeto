package pt.isel.ps.gis.model;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.model.types.NumrangeUserType;
import pt.isel.ps.gis.utils.RestrictionsUtils;
import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.*;
import java.util.ArrayList;
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
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "storage"
    )
    private Collection<StockItemMovement> stockitemmovements = new ArrayList<>();

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "storage"
    )
    private Collection<StockItemStorage> stockitemstorages = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
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

    public Storage(StorageId id, String storageName, Numrange storageTemperature) throws EntityException {
        this(storageName, storageTemperature);
        this.id = id;
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

    public void setId(Long houseId) throws EntityException {
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

    public void addStockItemMovement(StockItemMovement stockItemMovement) {
        stockitemmovements.add(stockItemMovement);
        stockItemMovement.setStorage(this);
    }

    public void removeStockItemMovement(StockItemMovement stockItemMovement) {
        stockitemmovements.remove(stockItemMovement);
        stockItemMovement.setStorage(null);
    }

    public void addStockItemStorage(StockItem stockItem, Short quantity) throws EntityException {
        StockItemStorage stockItemStorage = new StockItemStorage(id.getHouseId(), stockItem.getId().getStockitemSku(), id.getStorageId(), quantity);
        stockitemstorages.add(stockItemStorage);
        stockItem.getStockitemstorages().add(stockItemStorage);
    }

    public void removeStockItemStorage(StockItem stockItem) {
        for (StockItemStorage stockItemStorage : stockitemstorages) {
            if (stockItemStorage.getStorage().equals(this) && stockItemStorage.getStockitem().equals(stockItem)) {
                stockitemstorages.remove(stockItemStorage);
                stockItemStorage.getStockitem().getStockitemstorages().remove(stockItemStorage);
                stockItemStorage.setStorage(null);
                stockItemStorage.setStockitem(null);
            }
        }
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
