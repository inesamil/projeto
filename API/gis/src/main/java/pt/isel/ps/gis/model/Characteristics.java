package pt.isel.ps.gis.model;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.io.Serializable;
import java.util.Objects;

public class Characteristics implements Serializable {

    private Short house_babiesNumber;
    private Short house_childrenNumber;
    private Short house_adultsNumber;
    private Short house_seniorsNumber;

    protected Characteristics() {
    }

    public Characteristics(Short babiesNumber, Short childrenNumber, Short adultsNumber, Short seniorsNumber) throws EntityException {
        setHouse_babiesNumber(babiesNumber);
        setHouse_childrenNumber(childrenNumber);
        setHouse_adultsNumber(adultsNumber);
        setHouse_seniorsNumber(seniorsNumber);
    }

    public Short getHouse_babiesNumber() {
        return house_babiesNumber;
    }

    public void setHouse_babiesNumber(Short babiesNumber) throws EntityException {
        if (babiesNumber == null)
            babiesNumber = 0;
        ValidationsUtils.validateCharacteristicsBabiesNumber(babiesNumber);
        this.house_babiesNumber = babiesNumber;
    }

    public Short getHouse_childrenNumber() {
        return house_childrenNumber;
    }

    public void setHouse_childrenNumber(Short childrenNumber) throws EntityException {
        if (childrenNumber == null)
            childrenNumber = 0;
        ValidationsUtils.validateCharacteristicsChildrenNumber(childrenNumber);
        this.house_childrenNumber = childrenNumber;
    }

    public Short getHouse_adultsNumber() {
        return house_adultsNumber;
    }

    public void setHouse_adultsNumber(Short adultsNumber) throws EntityException {
        if (adultsNumber == null)
            adultsNumber = 0;
        ValidationsUtils.validateCharacteristicsAdultsNumber(adultsNumber);
        this.house_adultsNumber = adultsNumber;
    }

    public Short getHouse_seniorsNumber() {
        return house_seniorsNumber;
    }

    public void setHouse_seniorsNumber(Short seniorsNumber) throws EntityException {
        if (seniorsNumber == null)
            seniorsNumber = 0;
        ValidationsUtils.validateCharacteristicsSeniorsNumber(seniorsNumber);
        this.house_seniorsNumber = seniorsNumber;
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
