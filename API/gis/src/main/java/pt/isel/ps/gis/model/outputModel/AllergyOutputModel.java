package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.Action;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.Entity;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.Link;
import pt.isel.ps.gis.model.Allergy;
import pt.isel.ps.gis.utils.UriBuilderUtils;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"class", "properties", "entities", "actions", "links"})
public class AllergyOutputModel {

    private final static String ENTITY_CLASS = "allergy";

    @JsonProperty(value = "class")
    private final String[] klass;
    @JsonProperty
    private final Map<String, Object> properties;
    @JsonProperty
    private final Entity[] entities;
    @JsonProperty
    private final Action[] actions;
    @JsonProperty
    private final Link[] links;

    // Ctor
    public AllergyOutputModel(Allergy allergy) {
        this.klass = initKlass();
        this.properties = initProperties(allergy);
        this.entities = initEntities(allergy);
        this.actions = initActions(allergy);
        this.links = initLinks(allergy);
    }

    // Initters
    private String[] initKlass() {
        return new String[]{ENTITY_CLASS};
    }

    private HashMap<String, Object> initProperties(Allergy allergy) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("allergy-allergen", allergy.getAllergyAllergen());
        return properties;
    }

    private Entity[] initEntities(Allergy allergy) {
        // URIs
        String indexUri = UriBuilderUtils.buildIndexUri();

        // Subentities
        Entity index = new Entity(new String[]{"index"}, new String[]{"index"}, indexUri);

        return new Entity[]{index};
    }

    private Action[] initActions(Allergy allergy) {
        return new Action[]{};
    }

    private Link[] initLinks(Allergy allergy) {
        String allergen = allergy.getAllergyAllergen();

        // URIs
        String allergyUri = UriBuilderUtils.buildAllergyUri(allergen);

        // Link-self
        Link self = new Link(new String[]{"self"}, allergyUri);

        return new Link[]{self};
    }
}
