package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.Collection;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.Template;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Data;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Item;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Link;
import pt.isel.ps.gis.model.Storage;
import pt.isel.ps.gis.utils.UriBuilderUtils;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoragesOutputModel {

    @JsonProperty
    private final Collection collection;

    public StoragesOutputModel(long houseId, List<Storage> storages) {
        this.collection = initCollection(houseId, storages);
    }

    private Collection initCollection(long houseId, List<Storage> storages) {
        //URIs
        String houseUri = UriBuilderUtils.buildHouseUri(houseId);
        String storagesUri = UriBuilderUtils.buildStoragesUri(houseId);

        // Version
        String version = "1.0";

        // Link
        Link[] links = new Link[]{
                new Link("house", houseUri)
        };

        // Items
        Item[] items = mapItems(houseId, storages);

        // Template
        Template template = new Template(
                new Data[]{
                        new Data("storage-name", null, "Name"),
                        new Data("storage-minimum-temperature", null, "Minimum Temperature"),
                        new Data("storage-maximum-temperature", null, "Maximum Temperature")
                });

        return new Collection(version, storagesUri, links, items, template);
    }

    private Item[] mapItems(long houseId, List<Storage> storages) {
        int storagesSize = storages.size();
        Item[] items = new Item[storagesSize];
        for (int i = 0; i < storagesSize; i++) {
            Storage storage = storages.get(i);
            short storageId = storage.getId().getStorageId();
            items[i] = new Item(
                    UriBuilderUtils.buildStorageUri(houseId, storageId),
                    new Data[]{
                            new Data("house-id", houseId, "House ID"),
                            new Data("storage-id", storageId, "Storage ID"),
                            new Data("storage-name", storage.getStorageName(), "Name")
                    },
                    null
            );
        }
        return items;
    }
}
