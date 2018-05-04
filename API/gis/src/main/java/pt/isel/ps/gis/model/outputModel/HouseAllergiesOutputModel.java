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
public class HouseAllergiesOutputModel {

    @JsonProperty
    private final Collection collection;

    public HouseAllergiesOutputModel(long houseId, List<Allergy> allergies) {
        this.collection = initCollection(houseId, allergies);
    }

    private Collection initCollection(long houseId, List<Allergy> allergies) {
        //URIs
        String houseUri = UriBuilderUtils.buildHouseUri(houseId);
        String houseAllergiesUri = UriBuilderUtils.buildHouseAllergiesUri(houseId);

        // Version
        String version = "1.0";

        // Link
        Link[] links = new Link[]{
                new Link("house", houseUri)
        };

        // Items
        Item[] items = mapItems(houseId, allergies);

        return new Collection(version, houseAllergiesUri, links, items);
    }

    private Item[] mapItems(long houseId, List<Allergy> allergies) {
        int allergiesSize = allergies.size();
        Item[] items = new Item[allergiesSize];
        for (int i = 0; i < allergiesSize; i++) {
            Allergy allergy = allergies.get(i);
            String allergen = allergy.getAllergyAllergen();
            items[i] = new Item(
                    UriBuilderUtils.buildHouseAllergyUri(houseId, allergen),
                    new Data[]{
                            new Data("house-id", houseId, "House ID"),
                            new Data("allergy-allergen", allergen, "Allergen")
                    },
                    new Link[]{
                            new Link("stock-items-allergen", UriBuilderUtils.buildStockItemsAllergen(houseId, allergen))
                    }
            );
        }
        return items;
    }
}
