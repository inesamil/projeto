package pt.isel.ps.gis.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "house")
public class House {

    @Id
    @Column(name = "house_id")
    private Long houseId;

    @Basic
    @Column(name = "house_name")
    private String houseName;

    @Basic
    @Column(name = "house_characteristics")
    private Serializable houseCharacteristics;

    @OneToMany(mappedBy = "houseByHouseId")
    private Collection<HouseAllergy> houseallergiesByHouseId;

    @OneToMany(mappedBy = "houseByHouseId")
    private Collection<List> listsByHouseId;

    @OneToMany(mappedBy = "houseByHouseId")
    private Collection<StockItem> stockitemsByHouseId;

    @OneToMany(mappedBy = "houseByHouseId")
    private Collection<Storage> storagesByHouseId;

    @OneToMany(mappedBy = "houseByHouseId")
    private Collection<UserHouse> userhousesByHouseId;

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public Serializable getHouseCharacteristics() {
        return houseCharacteristics;
    }

    public void setHouseCharacteristics(Serializable houseCharacteristics) {
        this.houseCharacteristics = houseCharacteristics;
    }

    public Collection<HouseAllergy> getHouseallergiesByHouseId() {
        return houseallergiesByHouseId;
    }

    public void setHouseallergiesByHouseId(Collection<HouseAllergy> houseallergiesByHouseId) {
        this.houseallergiesByHouseId = houseallergiesByHouseId;
    }

    public Collection<List> getListsByHouseId() {
        return listsByHouseId;
    }

    public void setListsByHouseId(Collection<List> listsByHouseId) {
        this.listsByHouseId = listsByHouseId;
    }

    public Collection<StockItem> getStockitemsByHouseId() {
        return stockitemsByHouseId;
    }

    public void setStockitemsByHouseId(Collection<StockItem> stockitemsByHouseId) {
        this.stockitemsByHouseId = stockitemsByHouseId;
    }

    public Collection<Storage> getStoragesByHouseId() {
        return storagesByHouseId;
    }

    public void setStoragesByHouseId(Collection<Storage> storagesByHouseId) {
        this.storagesByHouseId = storagesByHouseId;
    }

    public Collection<UserHouse> getUserhousesByHouseId() {
        return userhousesByHouseId;
    }

    public void setUserhousesByHouseId(Collection<UserHouse> userhousesByHouseId) {
        this.userhousesByHouseId = userhousesByHouseId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        House that = (House) obj;
        return Objects.equals(houseId, that.houseId) &&
                Objects.equals(houseName, that.houseName) &&
                Objects.equals(houseCharacteristics, that.houseCharacteristics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(houseId, houseName, houseCharacteristics);
    }
}
