package pt.isel.ps.gis.model;

import java.io.Serializable;
import java.util.Objects;

public class Characteristics implements Serializable {

    private Short house_babiesNumber;
    private Short house_childrenNumber;
    private Short house_adultsNumber;
    private Short house_seniorsNumber;

    public Characteristics(Short house_babiesNumber, Short house_childrenNumber, Short house_adultsNumber, Short house_seniorsNumber) {
        // TODO validations. NOT NULL and between [0,100]
        this.house_babiesNumber = house_babiesNumber;
        this.house_childrenNumber = house_childrenNumber;
        this.house_adultsNumber = house_adultsNumber;
        this.house_seniorsNumber = house_seniorsNumber;
    }

    public Short getHouse_babiesNumber() {
        return house_babiesNumber;
    }

    public Short getHouse_childrenNumber() {
        return house_childrenNumber;
    }

    public Short getHouse_adultsNumber() {
        return house_adultsNumber;
    }

    public Short getHouse_seniorsNumber() {
        return house_seniorsNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Characteristics that = (Characteristics) obj;
        return Objects.equals(house_babiesNumber, that.house_babiesNumber) &&
                Objects.equals(house_childrenNumber, that.house_childrenNumber) &&
                Objects.equals(house_adultsNumber, that.house_adultsNumber) &&
                Objects.equals(house_seniorsNumber, that.house_seniorsNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(house_babiesNumber, house_childrenNumber, house_adultsNumber, house_seniorsNumber);
    }
}
