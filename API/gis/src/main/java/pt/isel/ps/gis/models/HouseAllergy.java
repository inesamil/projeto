package pt.isel.ps.gis.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "houseallergy")
public class HouseAllergy {

    @EmbeddedId
    private HouseAllergyId id;

    @Basic
    @Column(name = "houseallergy_alergicsnum")
    private Short houseallergyAlergicsnum;

    @ManyToOne
    @JoinColumn(name = "house_id", referencedColumnName = "house_id", nullable = false)
    private House houseByHouseId;

    @ManyToOne
    @JoinColumn(name = "allergy_allergen", referencedColumnName = "allergy_allergen", nullable = false)
    private Allergy allergyByAllergyAllergen;

    public HouseAllergyId getId() {
        return id;
    }

    public void setId(HouseAllergyId id) {
        this.id = id;
    }

    public Short getHouseallergyAlergicsnum() {
        return houseallergyAlergicsnum;
    }

    public void setHouseallergyAlergicsnum(Short houseallergyAlergicsnum) {
        this.houseallergyAlergicsnum = houseallergyAlergicsnum;
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
