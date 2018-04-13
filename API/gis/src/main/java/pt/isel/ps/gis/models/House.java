package pt.isel.ps.gis.models;

import pt.isel.ps.gis.exceptions.ModelException;
import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "house")
public class House {

    /**
     * COLUNAS
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_id", nullable = false)
    private Long houseId;

    @Basic
    @Column(name = "house_name", length = 35, nullable = false)
    private String houseName;

    @Basic
    @Column(name = "house_characteristics", nullable = false)
    private Serializable houseCharacteristics;

    /**
     * COLEÇÕES
     */
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

    /**
     * CONSTRUTORES
     */
    protected House() {
    }

    public House(String houseName, Serializable houseCharacteristics) throws ModelException {
        setHouseName(houseName);
        setHouseCharacteristics(houseCharacteristics);
    }

    public House(Long houseId, String houseName, Serializable houseCharacteristics) throws ModelException {
        setHouseId(houseId);
        setHouseName(houseName);
        setHouseCharacteristics(houseCharacteristics);
    }

    /**
     * GETTERS E SETTERS
     */
    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) throws ModelException, ModelException {
        ValidationsUtils.validateHouseId(houseId);
        this.houseId = houseId;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) throws ModelException {
        ValidationsUtils.validateHouseName(houseName);
        this.houseName = houseName;
    }

    public Serializable getHouseCharacteristics() {
        return houseCharacteristics;
    }

    public void setHouseCharacteristics(Serializable houseCharacteristics) {
        //TODO: validar Serializable
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
