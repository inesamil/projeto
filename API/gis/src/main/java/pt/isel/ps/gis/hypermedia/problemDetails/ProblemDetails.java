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

    public ProblemDetails(String type, String title, int status, String detail, String instance) {
        this.type = type;
        this.title = title;
        this.status = status;
        this.detail = detail;
        this.instance = instance;
    }

    public ProblemDetails(String title, int status, String detail, String instance) {
        this("about:blank", title, status, detail, instance);
    }

    public ProblemDetails(String title, int status, String detail) {
        this(title, status, detail, null);
    }
}