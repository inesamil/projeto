package pt.isel.ps.gis.model.inputModel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HouseInputModel {

    @JsonProperty("house-name")
    private String name;

    @JsonProperty("babies-number")
    private Short babiesNumber;

    @JsonProperty("children-number")
    private Short childrenNumber;

    @JsonProperty("adults-number")
    private Short adultsNumber;

    @JsonProperty("seniors-number")
    private Short seniorsNumber;

    HouseInputModel() {}

    HouseInputModel(String str) {
        System.out.println(str);
    }

    public String getName() {
        return name;
    }

    public Short getBabiesNumber() {
        return babiesNumber;
    }

    public Short getChildrenNumber() {
        return childrenNumber;
    }

    public Short getAdultsNumber() {
        return adultsNumber;
    }

    public Short getSeniorsNumber() {
        return seniorsNumber;
    }
}
