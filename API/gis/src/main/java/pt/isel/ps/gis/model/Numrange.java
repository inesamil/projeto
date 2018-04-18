package pt.isel.ps.gis.model;

import java.io.Serializable;

public class Numrange implements Serializable {

    private Float minimum;
    private Float maximum;

    public Numrange(Float minimum, Float maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public static Numrange parseNumrange(String s) throws NumberFormatException {
        String[] split = s.split("[\\[,\\]]");
        float minimum = Float.parseFloat(split[1]);
        float maximum = Float.parseFloat(split[2]);
        return new Numrange(minimum, maximum);
    }

    public Float getMinimum() {
        return minimum;
    }

    public void setMinimum(Float minimum) {
        this.minimum = minimum;
    }

    public Float getMaximum() {
        return maximum;
    }

    public void setMaximum(Float maximum) {
        this.maximum = maximum;
    }

    public String toString() {
        return "[" + minimum + ", " + maximum + "]";
    }
}
