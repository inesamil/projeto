package pt.isel.ps.gis.model.inputModel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InvitationInputModelUpdate {

    @JsonProperty("accept")
    private Boolean accept;

    public Boolean getAccept() {
        return accept;
    }
}
