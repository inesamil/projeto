package pt.isel.ps.gis.model.inputModel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HouseholdInputModel {

    @JsonProperty("household-administrator")
    private Boolean administrator;

    public Boolean getAdministrator() {
        return administrator;
    }
}
