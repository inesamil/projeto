package pt.isel.ps.gis.hypermedia.jsonHome.components.members;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResourceObject {

    private final String href;
    private final String hrefTemplate;
    private final HashMap<String, String> hrefVars;
    private final Hints hints;

    public ResourceObject(String href, String hrefTemplate, HashMap<String, String> hrefVars, Hints hints) {
        this.href = href;
        this.hrefTemplate = hrefTemplate;
        this.hrefVars = hrefVars;
        this.hints = hints;
    }

    public String getHref() {
        return href;
    }

    public String getHrefTemplate() {
        return hrefTemplate;
    }

    public HashMap<String, String> getHrefVars() {
        return hrefVars;
    }

    public Hints getHints() {
        return hints;
    }
}
