package pt.isel.ps.gis.DAL.bdModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Allergy")
public class Allergy {

    @Id
    @Column(name = "allergy_allergen", length = 75, nullable = false)
    private String allergen;

    protected Allergy() {
    }

    public Allergy(String allergen) {
        this.allergen = allergen;
    }
}
