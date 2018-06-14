package pt.isel.ps.gis.model;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.model.types.CharacteristicsJsonUserType;
import pt.isel.ps.gis.utils.RestrictionsUtils;
import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "house")
@TypeDef(name = "CharacteristicsJsonUserType", typeClass = CharacteristicsJsonUserType.class)
public class House {

    /**
     * COLUNAS
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_id", nullable = false)
    private Long houseId;

    @Basic
    @Column(name = "house_name", length = RestrictionsUtils.HOUSE_NAME_MAX_LENGTH, nullable = false)
    private String houseName;

    @Basic
    @Column(name = "house_characteristics", nullable = false, columnDefinition = "json")
    @Type(type = "CharacteristicsJsonUserType")
    private Characteristics houseCharacteristics;

    /**
     * COLEÇÕES
     */
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "houseByHouseId"
    )
    private Collection<HouseAllergy> houseallergiesByHouseId = new ArrayList<>();

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "houseByHouseId"
    )
    private Collection<List> listsByHouseId = new ArrayList<>();

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "houseByHouseId"
    )
    private Collection<StockItem> stockitemsByHouseId = new ArrayList<>();

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "houseByHouseId"
    )
    private Collection<Storage> storagesByHouseId = new ArrayList<>();

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "houseByHouseId"
    )
    private Collection<UserHouse> userhousesByHouseId = new ArrayList<>();

    /**
     * CONSTRUTORES
     */
    protected House() {
    }

    public House(String houseName, Characteristics houseCharacteristics) throws EntityException {
        setHouseName(houseName);
        setHouseCharacteristics(houseCharacteristics);
    }

    public House(Long houseId, String houseName, Characteristics houseCharacteristics) throws EntityException {
        this(houseName, houseCharacteristics);
        setHouseId(houseId);
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

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) throws EntityException {
        ValidationsUtils.validateHouseName(houseName);
        this.houseName = houseName;
    }

    public Characteristics getHouseCharacteristics() {
        return houseCharacteristics;
    }

    public void setHouseCharacteristics(Characteristics houseCharacteristics) {
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

    public void addList(List list) {
        listsByHouseId.add(list);
        list.setHouseByHouseId(this);
    }

    public void removeList(List list) {
        listsByHouseId.remove(list);
        list.setHouseByHouseId(null);
    }

    public void addHouseAllergy(Allergy allergy, Short allergicsNum) throws EntityException {
        HouseAllergy houseAllergy = new HouseAllergy(houseId, allergy.getAllergyAllergen(), allergicsNum);
        houseallergiesByHouseId.add(houseAllergy);
        allergy.getHouseallergiesByAllergyAllergen().add(houseAllergy);
    }

    public void removeHouseAllergy(Allergy allergy) {
        for (HouseAllergy houseAllergy : houseallergiesByHouseId) {
            if (houseAllergy.getHouseByHouseId().equals(this) && houseAllergy.getAllergyByAllergyAllergen().equals(allergy)) {
                houseallergiesByHouseId.remove(houseAllergy);
                houseAllergy.getAllergyByAllergyAllergen().getHouseallergiesByAllergyAllergen().remove(houseAllergy);
                houseAllergy.setHouseByHouseId(null);
                houseAllergy.setAllergyByAllergyAllergen(null);
            }
        }
    }

    public void addStockItem(StockItem stockItem) {
        stockitemsByHouseId.add(stockItem);
        stockItem.setHouseByHouseId(this);
    }

    public void removeStockItem(StockItem stockItem) {
        stockitemsByHouseId.remove(stockItem);
        stockItem.setHouseByHouseId(null);
    }

    public void addStorage(Storage storage) {
        storagesByHouseId.add(storage);
        storage.setHouseByHouseId(this);
    }

    public void removeStorage(Storage storage) {
        storagesByHouseId.remove(storage);
        storage.setHouseByHouseId(null);
    }

    public void addUserHouse(Users user, Boolean administrator) throws EntityException {
        UserHouse userHouse = new UserHouse(houseId, user.getUsersId(), administrator);
        userhousesByHouseId.add(userHouse);
        user.getUserhousesByUsersId().add(userHouse);
    }

    public void removeUserHouse(Users user) {
        for (UserHouse userHouse : userhousesByHouseId) {
            if (userHouse.getHouseByHouseId().equals(this) && userHouse.getUsersByUsersId().equals(user)) {
                userhousesByHouseId.remove(userHouse);
                userHouse.getHouseByHouseId().getUserhousesByHouseId().remove(userHouse);
                userHouse.setHouseByHouseId(null);
                userHouse.setUsersByUsersId(null);
            }
        }
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
