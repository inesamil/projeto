package pt.isel.ps.gis.hypermedia.problemDetails;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProblemDetails {

    @JsonProperty
    private final String type;

    @JsonProperty
    private final String title;

    @JsonProperty
    private final int status;

    @JsonProperty
    private final String detail;

    @JsonProperty
    private final String instance;

    @JsonProperty
    private final String userFriendlyMessage;

    public ProblemDetails(String type, String title, int status, String detail, String instance, String userFriendlyMessage) {
        this.type = type;
        this.title = title;
        this.status = status;
        this.detail = detail;
        this.instance = instance;
        this.userFriendlyMessage = userFriendlyMessage;
    }

    public ProblemDetails(String title, int status, String detail, String userFriendlyMessage, String instance) {
        this("about:blank", title, status, detail, instance, userFriendlyMessage);
    }

    public ProblemDetails(String title, int status, String detail, String userFriendlyMessage) {
        this(title, status, detail,null, userFriendlyMessage);
    }
}