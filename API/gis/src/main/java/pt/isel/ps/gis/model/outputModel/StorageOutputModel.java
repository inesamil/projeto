package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.*;
import pt.isel.ps.gis.model.Storage;
import pt.isel.ps.gis.utils.UriBuilderUtils;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"class", "properties", "actions", "links"})
public class StorageOutputModel {

    private final static String ENTITY_CLASS = "storage";

    @JsonProperty(value = "class")
    private final String[] klass;
    @JsonProperty
    private final Map<String, Object> properties;
    @JsonProperty
    private final Action[] actions;
    @JsonProperty
    private final Link[] links;

    // Ctor
    public StorageOutputModel(Storage storage) {
        this.klass = initKlass();
        this.properties = initProperties(storage);
        this.actions = initActions(storage);
        this.links = initLinks(storage);
    }

    // Initters
    private String[] initKlass() {
        return new String[]{ENTITY_CLASS};
    }

    private HashMap<String, Object> initProperties(Storage storage) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("house-id", storage.getId().getHouseId());
        properties.put("storage-id", storage.getId().getStorageId());
        properties.put("storage-name", storage.getStorageName());
        properties.put("storage-temperature", storage.getStorageTemperature());
        return properties;
    }

    private Action[] initActions(Storage storage) {
        Long houseId = storage.getId().getHouseId();
        Short storageId = storage.getId().getStorageId();

        // Type
        String type = "application/x-www-form-urlencoded";

        // URIs
        String storageUri = UriBuilderUtils.buildStorageUri(houseId, storageId);

        // PUT Storage
        Action putStorage = new Action(
                "update-storage",
                "Update Storage",
                Method.PUT,
                storageUri,
                type,
                new Field[]{
                        new Field("storage-minimum-temperature", Field.Type.number, null, "Minimum Temperature"),
                        new Field("storage-maximum-temperature", Field.Type.number, null, "Maximum Temperature"),
                        new Field("storage-name", Field.Type.text, null, "Name")
                }
        );

        // DELETE Storage
        Action deleteStorage = new Action(
                "delete-storage",
                "Delete Storage",
                Method.DELETE,
                storageUri,
                type,
                null
        );

        return new Action[]{putStorage, deleteStorage};
    }

    private Link[] initLinks(Storage storage) {
        Long houseId = storage.getId().getHouseId();
        Short storageId = storage.getId().getStorageId();

        // URIs
        String storageUri = UriBuilderUtils.buildStorageUri(houseId, storageId);
        String storagesUri = UriBuilderUtils.buildStoragesUri(houseId);

        // Link-self
        Link self = new Link(new String[]{"self"}, new String[]{ENTITY_CLASS}, storageUri);
        // Link-related-storages
        Link storagesLink = new Link(new String[]{"related"}, new String[]{"storages", "collection"}, storagesUri);

        return new Link[]{self, storagesLink};
    }
}
