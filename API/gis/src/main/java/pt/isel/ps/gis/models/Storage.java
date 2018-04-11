package pt.isel.ps.gis.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "storage")
public class Storage {

    @EmbeddedId
    private StorageId id;

    @Basic
    @Column(name = "storage_name")
    private String storageName;

    @Basic
    @Column(name = "storage_temperature")
    private Serializable storageTemperature;

    @OneToMany(mappedBy = "storage")
    private Collection<StockItemMovement> stockitemmovements;

    @OneToMany(mappedBy = "storage")
    private Collection<StockItemStorage> stockitemstorages;

    @ManyToOne
    @JoinColumn(name = "house_id", referencedColumnName = "house_id", nullable = false)
    private House houseByHouseId;

    public StorageId getId() {
        return id;
    }

    public void setId(StorageId id) {
        this.id = id;
    }

    public String getStorageName() {
        return storageName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    public Serializable getStorageTemperature() {
        return storageTemperature;
    }

    public void setStorageTemperature(Serializable storageTemperature) {
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
