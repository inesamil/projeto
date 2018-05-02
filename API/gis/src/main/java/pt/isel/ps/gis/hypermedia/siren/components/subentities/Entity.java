package pt.isel.ps.gis.hypermedia.siren.components.subentities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"class", "rel", "href"})
public class Entity {

    @JsonProperty(value = "class")
    private String[] klass;
    private String[] rel;
    private String href;

    public Entity(String[] klass, String[] rel, String href) {
        this.klass = klass;
        this.rel = rel;
        this.href = href;
    }

    public String[] getKlass() {
        return klass;
    }

    public String[] getRel() {
        return rel;
    }

    public String getHref() {
        return href;
    }
}
