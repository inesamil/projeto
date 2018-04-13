package pt.isel.ps.gis.model;

import pt.isel.ps.gis.exceptions.ModelException;
import pt.isel.ps.gis.utils.RestrictionsUtils;
import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "allergy")
public class Allergy {

    /**
     * COLUNAS
     */
    @Id
    @Column(name = "allergy_allergen", length = RestrictionsUtils.ALLERGY_ALLERGEN_MAX_LENGTH, nullable = false)
    private String allergyAllergen;

    /**
     * ASSOCIAÇÕES
     */
    @OneToMany(mappedBy = "allergyByAllergyAllergen")
    private Collection<HouseAllergy> houseallergiesByAllergyAllergen;

    @OneToMany(mappedBy = "allergyByAllergyAllergen")
    private Collection<StockItemAllergy> stockitemallergiesByAllergyAllergen;

    /**
     * CONSTRUTORES
     */
    protected Allergy() {
    }

    public Allergy(String allergyAllergen) throws ModelException {
        setAllergyAllergen(allergyAllergen);
    }

    /**
     * GETTERS E SETTERS
     */
    public String getAllergyAllergen() {
        return allergyAllergen;
    }

    public void setAllergyAllergen(String allergyAllergen) throws ModelException {
        ValidationsUtils.validateAllergyAllergen(allergyAllergen);
        this.allergyAllergen = allergyAllergen;
    }

    public Collection<HouseAllergy> getHouseallergiesByAllergyAllergen() {
        return houseallergiesByAllergyAllergen;
    }

    public void setHouseallergiesByAllergyAllergen(Collection<HouseAllergy> houseallergiesByAllergyAllergen) {
        this.houseallergiesByAllergyAllergen = houseallergiesByAllergyAllergen;
    }

    public Collection<StockItemAllergy> getStockitemallergiesByAllergyAllergen() {
        return stockitemallergiesByAllergyAllergen;
    }

    public void setStockitemallergiesByAllergyAllergen(Collection<StockItemAllergy> stockitemallergiesByAllergyAllergen) {
        this.stockitemallergiesByAllergyAllergen = stockitemallergiesByAllergyAllergen;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Allergy that = (Allergy) obj;
        return Objects.equals(allergyAllergen, that.allergyAllergen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(allergyAllergen);
    }
}
