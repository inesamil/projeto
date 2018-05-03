package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.Collection;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Data;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Item;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Link;
import pt.isel.ps.gis.model.Allergy;
import pt.isel.ps.gis.utils.UriBuilderUtils;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AllergiesOutputModel {

    @JsonProperty
    private final Collection collection;

    public AllergiesOutputModel(List<Allergy> allergies) {
        this.collection = initCollection(allergies);
    }

    private Collection initCollection(List<Allergy> allergies) {
        //URIs
        String indexUri = UriBuilderUtils.buildIndexUri();
        String allergiesUri = UriBuilderUtils.buildAllergiesUri();

        // Version
        String version = "1.0";

        // Link
        Link[] links = new Link[]{
                new Link("index", indexUri)
        };

        // Items
        Item[] items = mapItems(allergies);

        return new Collection(version, allergiesUri, links, items, null);
    }

    private Item[] mapItems(List<Allergy> allergies) {
        int allergiesSize = allergies.size();
        Item[] items = new Item[allergiesSize];
        for (int i = 0; i < allergiesSize; i++) {
            Allergy allergy = allergies.get(i);
            items[i] = new Item(
                    null,
                    new Data[]{
                            new Data("allergy-allergen", allergy.getAllergyAllergen(), "Allergen")
                    },
                    null
            );
        }
        return items;
    }
}
