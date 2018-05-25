package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.*;
import pt.isel.ps.gis.model.Allergy;
import pt.isel.ps.gis.model.HouseAllergy;
import pt.isel.ps.gis.utils.UriBuilderUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"class", "properties", "entities", "actions", "links"})
public class HouseAllergiesOutputModel {

    private final static String ENTITY_CLASS = "house-allergies";

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

    public HouseAllergiesOutputModel(long houseId, List<HouseAllergy> houseAllergies) {
        this.klass = initKlass();
        this.properties = initProperties(houseAllergies);
        this.entities = initEntities(houseId, houseAllergies);
        this.actions = initActions(houseId);
        this.links = initLinks(houseId);
    }

    private String[] initKlass() {
        return new String[]{ENTITY_CLASS, "collection"};
    }

    private Map<String, Object> initProperties(List<HouseAllergy> houseAllergies) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("size", houseAllergies.size());

        return properties;
    }

    private Entity[] initEntities(long houseId, List<HouseAllergy> houseAllergies) {
        Entity[] entities = new Entity[houseAllergies.size()];
        // Type
        String type = "application/json";

        for (int i = 0; i < houseAllergies.size(); ++i) {
            HouseAllergy houseAllergy = houseAllergies.get(i);
            Allergy allergy = houseAllergy.getAllergyByAllergyAllergen();
            String allergen = allergy.getAllergyAllergen();

            HashMap<String, Object> properties = new HashMap<>();
            properties.put("house-id", houseId);
            properties.put("allergy-allergen", allergen);
            properties.put("house-allergies-num", houseAllergy.getHouseallergyAlergicsnum());

            String houseAllergyUri = UriBuilderUtils.buildHouseAllergyUri(houseId, allergen);

            Action putHouseAllergy = new Action(
                    "update-house-allergy",
                    "Update House Allergy",
                    Method.PUT,
                    houseAllergyUri,
                    type,
                    new Field[]{
                            new Field("allergy-allergen", Field.Type.text, null, "Allergen Name"),
                            new Field("house-allergies-num", Field.Type.number, null, "Allergies Number")
                    }
            );
            Action deleteHouseAllergy = new Action(
                    "delete-house-allergy",
                    "Delete House Allergy",
                    Method.DELETE,
                    houseAllergyUri,
                    null,
                    null
            );

            String stockItemsAllergenUri = UriBuilderUtils.buildStockItemsAllergenUri(houseId, allergen);
            entities[i] = new Entity(
                    new String[]{"stock-items-allergen", "collection"},
                    new String[]{"collection"},
                    properties,
                    new Action[]{putHouseAllergy, deleteHouseAllergy},
                    new Link[]{new Link(new String[]{"self"}, new String[]{"stock-items-allergen", "collection"}, stockItemsAllergenUri)});
        }
        return entities;
    }

    private Action[] initActions(long houseId) {
        // Type
        String type = "application/json";

        //URIs
        String houseAllergiesUri = UriBuilderUtils.buildHouseAllergiesUri(houseId);

        // POST house allergy
        Action putHouseAllergy = new Action(
                "update-house-allergy",
                "Update House Allergy",
                Method.PUT,
                houseAllergiesUri,
                type,
                new Field[]{
                        new Field("allergy-allergen", Field.Type.text, null, "Allergen Name"),
                        new Field("house-allergies-num", Field.Type.number, null, "Allergies Number")
                }
        );

        // DELETE house allergy
        Action deleteHouseAllergies = new Action(
                "delete-house-allergies",
                "Delete House Allergies",
                Method.DELETE,
                houseAllergiesUri,
                null,
                null
        );

        return new Action[]{putHouseAllergy, deleteHouseAllergies};
    }

    private Link[] initLinks(long houseId) {
        //URIs
        String houseUri = UriBuilderUtils.buildHouseUri(houseId);
        String houseAllergiesUri = UriBuilderUtils.buildHouseAllergiesUri(houseId);

        //Link-related-house
        Link houseLink = new Link(new String[]{"house"}, new String[]{"related"}, houseUri);
        // Link-self
        Link self = new Link(new String[]{"self"}, new String[]{ENTITY_CLASS, "collection"}, houseAllergiesUri);

        return new Link[]{self, houseLink};
    }
}
