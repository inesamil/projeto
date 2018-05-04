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
public class AllergiesStockItemOutputModel {

    @JsonProperty
    private final Collection collection;

    public AllergiesStockItemOutputModel(long houseId, String sku, List<Allergy> allergies) {
        this.collection = initCollection(houseId, sku, allergies);
    }

    private Collection initCollection(long houseId, String sku, List<Allergy> allergies) {
        //URIs
        String stockItemUri = UriBuilderUtils.buildStockItemUri(houseId, sku);
        String allergiesStockItemUri = UriBuilderUtils.buildAllergiesStockItemUri(houseId, sku);

        // Version
        String version = "1.0";

        // Link
        Link[] links = new Link[]{
                new Link("stock-item", stockItemUri)
        };

        // Items
        Item[] items = mapItems(houseId, sku, allergies);

        return new Collection(version, allergiesStockItemUri, links, items);
    }

    private Item[] mapItems(long houseId, String sku, List<Allergy> allergies) {
        int allergiesSize = allergies.size();
        Item[] items = new Item[allergiesSize];
        for (int i = 0; i < allergiesSize; i++) {
            Allergy allergy = allergies.get(i);
            items[i] = new Item(
                    null,
                    new Data[]{
                            new Data("house-id", houseId, "House ID"),
                            new Data("stock-item-id", sku, "Stock Item ID"),
                            new Data("allergy-allergen", allergy.getAllergyAllergen(), "Allergen")
                    },
                    null
            );
        }
        return items;
    }
}
