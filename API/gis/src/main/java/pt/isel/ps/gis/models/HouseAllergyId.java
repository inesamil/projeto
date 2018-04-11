package pt.isel.ps.gis.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class HouseAllergyId implements Serializable {

    @Id
    @Column(name = "house_id", nullable = false)
    private Long houseId;

    @Id
    @Column(name = "allergy_allergen", length = 75, nullable = false)
    private String allergyAllergen;

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public String getAllergyAllergen() {
        return allergyAllergen;
    }

    public void setAllergyAllergen(String allergyAllergen) {
        this.allergyAllergen = allergyAllergen;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        HouseAllergyId that = (HouseAllergyId) obj;
        return Objects.equals(houseId, that.houseId) &&
                Objects.equals(allergyAllergen, that.allergyAllergen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(houseId, allergyAllergen);
    }
}
