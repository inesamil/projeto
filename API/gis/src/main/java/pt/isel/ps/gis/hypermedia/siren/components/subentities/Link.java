package pt.isel.ps.gis.hypermedia.siren.components.subentities;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Link {

    private String[] rel;
    private String href;

    public Link(String[] rel, String href) {
        this.rel = rel;
        this.href = href;
    }

    public String[] getRel() {
        return rel;
    }

    public String getHref() {
        return href;
    }
}
