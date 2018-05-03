package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.Collection;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.Template;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Data;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Item;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Link;
import pt.isel.ps.gis.model.StockItemMovement;
import pt.isel.ps.gis.utils.UriBuilderUtils;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovementsOutputModel {

    @JsonProperty
    private final Collection collection;

    public MovementsOutputModel(long houseId, List<StockItemMovement> movements) {
        this.collection = initCollection(houseId, movements);
    }

    private Collection initCollection(long houseId, List<StockItemMovement> movements) {
        //URIs
        String houseUri = UriBuilderUtils.buildHouseUri(houseId);
        String movementsUri = UriBuilderUtils.buildMovementsUri(houseId);

        // Version
        String version = "1.0";

        // Link
        Link[] links = new Link[]{
                new Link("house", houseUri)
        };

        // Items
        Item[] items = mapItems(houseId, movements);

        // Template
        Template template = new Template(
                new Data[]{
                        new Data("house-id", houseId, "House ID"),
                        new Data("storage-id", null, "Storage ID"),
                        new Data("movement-type", null, "Type"),
                        new Data("movement-quantity", null, "Quantity"),
                        new Data("movement-info", null, "Info")
                });

        return new Collection(version, movementsUri, links, items, template);
    }

    private Item[] mapItems(long houseId, List<StockItemMovement> movements) {
        int movementsSize = movements.size();
        Item[] items = new Item[movementsSize];
        for (int i = 0; i < movementsSize; i++) {
            StockItemMovement movement = movements.get(i);
            short storageId = movement.getId().getStorageId();
            String sku = movement.getId().getStockitemSku();
            String datetime = movement.getId().getStockitemmovementDatetime();
            boolean type = movement.getId().getStockitemmovementType();
            items[i] = new Item(
                    null,
                    new Data[]{
                            new Data("house-id", houseId, "House ID"),
                            new Data("storage-id", storageId, "Storage ID"),
                            new Data("stock-item-id", sku, "Stock Item ID"),
                            new Data("movement-datetime", datetime, "Datetime"),
                            new Data("movement-type", type, "Type"),
                            new Data("movement-quantity", movement.getId().getStockitemmovementQuantity(),
                                    "Quantity")
                    },
                    null
            );
        }
        return items;
    }
}
