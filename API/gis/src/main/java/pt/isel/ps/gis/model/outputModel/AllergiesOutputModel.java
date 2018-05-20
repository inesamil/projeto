package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.*;
import pt.isel.ps.gis.model.Allergy;
import pt.isel.ps.gis.utils.UriBuilderUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"class", "properties", "entities", "actions", "links"})
public class AllergiesOutputModel {

    private final static String ENTITY_CLASS = "allergies";

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
    public AllergiesOutputModel(List<Allergy> allergies) {
        this.klass = initKlass();
        this.properties = initProperties(allergies);
        this.entities = initEntities(allergies);
        this.actions = initActions();
        this.links = initLinks();
    }

    private String[] initKlass() {
        return new String[]{ENTITY_CLASS, "collection"};
    }

    private Map<String, Object> initProperties(List<Allergy> allergies) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("size", allergies.size());

        return properties;
    }

    private Entity[] initEntities(List<Allergy> allergies) {
        Entity[] entities = new Entity[allergies.size()];
        for (int i = 0; i < allergies.size(); ++i) {
            Allergy allergy = allergies.get(i);

            HashMap<String, Object> properties = new HashMap<>();
            properties.put("allergy_allergen", allergy.getAllergyAllergen());

            entities[i] = new Entity(new String[]{"allergy"}, new String[]{"item"}, properties, null);
        }
        return entities;
    }

    private Action[] initActions() {
        // Type
        String type = "application/json";

        //URIs
        String allergiesUri = UriBuilderUtils.buildAllergiesUri();

        // POST allergy
        Action postAllergy = new Action(
                "add-allergy",
                "Add Allergy",
                Method.POST,
                allergiesUri,
                type,
                new Field[]{
                        new Field("allergy-allergen", Field.Type.text, null, "Name")
                }
        );

        // DELETE allergies
        Action deleteAllergies = new Action(
                "delete-allergies",
                "Delete Allergies",
                Method.DELETE,
                allergiesUri,
                null,
                null
        );

        return new Action[]{postAllergy, deleteAllergies};
    }

    private Link[] initLinks() {
        //URIs
        String indexUri = UriBuilderUtils.buildIndexUri();
        String allergiesUri = UriBuilderUtils.buildAllergiesUri();

        //Link-author-user
        Link indexLink = new Link(new String[]{"index"}, new String[]{"index"}, indexUri);
        // Link-self
        Link self = new Link(new String[]{"self"}, new String[]{ENTITY_CLASS, "collection"}, allergiesUri);

        return new Link[]{self, indexLink};
    }

}
