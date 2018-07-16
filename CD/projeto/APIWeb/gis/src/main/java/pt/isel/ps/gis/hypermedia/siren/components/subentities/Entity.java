package pt.isel.ps.gis.hypermedia.siren.components.subentities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"class", "rel", "properties", "href"})
public class Entity {
    @JsonProperty(value = "class")
    public String[] klass;
    public String[] rel;
    public HashMap<String, Object> properties;
    public Action[] actions;
    public Link[] links;

    public Entity(String[] klass, String[] rel, HashMap<String, Object> properties, Action[] actions, Link[] links) {
        this.klass = klass;
        this.rel = rel;
        this.properties = properties;
        this.actions = actions;
        this.links = links;
    }
}
