package pt.isel.ps.gis.model;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "houseallergy")
public class HouseAllergy {

    /**
     * COLUNAS
     */
    @EmbeddedId
    private HouseAllergyId id;

    @Basic
    @Column(name = "houseallergy_alergicsnum", nullable = false)
    private Short houseallergyAlergicsnum;

    /**
     * ASSOCIAÇÕES
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id", referencedColumnName = "house_id", nullable = false, insertable = false, updatable = false)
    private House houseByHouseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "allergy_allergen", referencedColumnName = "allergy_allergen", nullable = false, insertable = false, updatable = false)
    private Allergy allergyByAllergyAllergen;

    /**
     * CONSTRUTORES
     */
    protected HouseAllergy() {
    }

    public HouseAllergy(HouseAllergyId id, Short alergicsNum) throws EntityException {
        this.id = id;
        setHouseallergyAlergicsnum(alergicsNum);
    }

    public HouseAllergy(Long houseId, String allergy, Short alergicsNum) throws EntityException {
        setId(houseId, allergy);
        setHouseallergyAlergicsnum(alergicsNum);
    }

    /**
     * GETTERS E SETTERS
     */
    public HouseAllergyId getId() {
        return id;
    }

    public void setId(HouseAllergyId id) {
        this.id = id;
    }

    public void setId(Long houseId, String allergy) throws EntityException {
        setId(new HouseAllergyId(houseId, allergy));
    }

    public Short getHouseallergyAlergicsnum() {
        return houseallergyAlergicsnum;
    }

    public void setHouseallergyAlergicsnum(Short alergicsNum) throws EntityException {
        ValidationsUtils.validateHouseAllergyAllergicsNum(alergicsNum);
        this.houseallergyAlergicsnum = alergicsNum;
    }

    public House getHouseByHouseId() {
        return houseByHouseId;
    }

    public void setHouseByHouseId(House houseByHouseId) {
        this.houseByHouseId = houseByHouseId;
    }

    public Allergy getAllergyByAllergyAllergen() {
        return allergyByAllergyAllergen;
    }

    public void setAllergyByAllergyAllergen(Allergy allergyByAllergyAllergen) {
        this.allergyByAllergyAllergen = allergyByAllergyAllergen;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        HouseAllergy that = (HouseAllergy) obj;
        return Objects.equals(id, that.id) &&
                Objects.equals(houseallergyAlergicsnum, that.houseallergyAlergicsnum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, houseallergyAlergicsnum);
    }
}
