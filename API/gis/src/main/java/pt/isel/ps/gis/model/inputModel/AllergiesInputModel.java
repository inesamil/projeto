package pt.isel.ps.gis.model.inputModel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AllergiesInputModel {

    @JsonProperty(value = "allergies")
    private Allergy[] allergies;

    public Allergy[] getAllergies() {
        return allergies;
    }

    class Allergy {
        @JsonProperty(value = "allergy")
        private String allergy;

        @JsonProperty(value = "allergics-number")
        private Short allergicsNum;

        public String getAllergy() {
            return allergy;
        }

        public Short getAllergicsNum() {
            return allergicsNum;
        }
    }
}