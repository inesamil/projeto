package pt.isel.ps.gis.model;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.io.Serializable;
import java.util.Objects;

public class Numrange implements Serializable {

    private Float minimum;
    private Float maximum;

    public Numrange(Float minimum, Float maximum) throws EntityException {
        setRange(minimum, maximum);
    }

    private void setRange(Float minimum, Float maximum) throws EntityException {
        ValidationsUtils.validateRangeTemperature(minimum, maximum);
        setMinimum(minimum);
        setMaximum(maximum);
    }

    public Float getMinimum() {
        return minimum;
    }

    private void setMinimum(Float minimum) {
        this.minimum = minimum;
    }

    public Float getMaximum() {
        return maximum;
    }

    private void setMaximum(Float maximum) {
        this.maximum = maximum;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Numrange that = (Numrange) obj;
        return Objects.equals(minimum, that.minimum) &&
                Objects.equals(maximum, that.maximum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(minimum, maximum);
    }

    public String toString() {
        return "[" + minimum + ", " + maximum + "]";
    }

    public static Numrange parseNumrange(String s) throws NumberFormatException, EntityException {
        String[] split = s.split("[\\[,\\]]");
        float minimum = Float.parseFloat(split[1]);
        float maximum = Float.parseFloat(split[2]);
        return new Numrange(minimum, maximum);
    }
}
