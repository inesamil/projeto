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
public class AllergiesStockItemOutputModel {

    private final static String ENTITY_CLASS = "allergies-stock-item";

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

    public AllergiesStockItemOutputModel(long houseId, String sku, List<Allergy> allergies) {
        this.klass = initKlass();
        this.properties = initProperties(allergies);
        this.entities = initEntities(houseId, sku, allergies);
        this.actions = initActions(houseId, sku);
        this.links = initLinks(houseId, sku, allergies);
    }

    private String[] initKlass() {
        return new String[]{ENTITY_CLASS, "collection"};
    }

    private Map<String, Object> initProperties(List<Allergy> allergies) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("size", allergies.size());

        return properties;
    }

    private Entity[] initEntities(long houseId, String sku, List<Allergy> allergies) {
        Entity[] entities = new Entity[allergies.size()];
        for (int i = 0; i < allergies.size(); ++i) {
            Allergy allergy = allergies.get(i);

            HashMap<String, Object> properties = new HashMap<>();
            properties.put("house-id", houseId);
            properties.put("stock-item-id", sku);
            properties.put("allergy_allergen", allergy.getAllergyAllergen());

            entities[i] = new Entity(new String[]{"allergy"}, new String[]{"item"}, properties, null);
        }
        return entities;
    }

    private Action[] initActions(long houseId, String sku) {
        // Type
        String type = "application/json";

        //URIs
        String allergiesStockItemUri = UriBuilderUtils.buildAllergiesStockItemUri(houseId, sku);

        // POST allergy
        Action postAllergy = new Action(
                "add-allergy",
                "Add Allergy",
                Method.POST,
                allergiesStockItemUri,
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
                allergiesStockItemUri,
                null,
                null
        );

        return new Action[]{postAllergy, deleteAllergies};
    }

    private Link[] initLinks(long houseId, String sku, List<Allergy> allergies) {
        //URIs
        String indexUri = UriBuilderUtils.buildIndexUri();
        String allergiesStockItemUri = UriBuilderUtils.buildAllergiesStockItemUri(houseId, sku);
        String stockItemUri = UriBuilderUtils.buildStockItemUri(houseId, sku);

        //Link-author-user
        Link indexLink = new Link(new String[]{"index"}, new String[]{"index"}, indexUri);
        //Link-related-stockItem
        Link stockItemLink = new Link(new String[]{"stock-item"}, new String[]{"related"}, stockItemUri);
        // Link-self
        Link self = new Link(new String[]{"self"}, new String[]{ENTITY_CLASS, "collection"}, allergiesStockItemUri);

        return new Link[]{self, indexLink, stockItemLink};
    }
}
