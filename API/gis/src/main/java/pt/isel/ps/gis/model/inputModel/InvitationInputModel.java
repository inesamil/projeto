package pt.isel.ps.gis.model.inputModel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InvitationInputModel {

    @JsonProperty("user-username")
    private String username;

    public String getUsername() {
        return username;
    }
}