package pt.isel.ps.gis.hypermedia.problemPlusJson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProblemPlusJson {

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

    public ProblemPlusJson(String type, String title, int status, String detail, String instance) {
        this.type = type;
        this.title = title;
        this.status = status;
        this.detail = detail;
        this.instance = instance;
    }

    public ProblemPlusJson(String title, int status, String detail, String instance) {
        this("about:blank", title, status, detail, instance);
    }

    public ProblemPlusJson(String title, int status, String detail) {
        this(title, status, detail, null);
    }
}