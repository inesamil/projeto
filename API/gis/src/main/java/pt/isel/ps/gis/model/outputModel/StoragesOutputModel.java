package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.*;
import pt.isel.ps.gis.model.Storage;
import pt.isel.ps.gis.utils.UriBuilderUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"class", "properties", "entities", "actions", "links"})
public class StoragesOutputModel {

    private final static String ENTITY_CLASS = "storages";

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

    public StoragesOutputModel(long houseId, List<Storage> storages) {
        this.klass = initKlass();
        this.properties = initProperties(storages);
        this.entities = initEntities(houseId, storages);
        this.actions = initActions(houseId);
        this.links = initLinks(houseId);
    }

    // Initters
    private String[] initKlass() {
        return new String[]{ENTITY_CLASS, "collection"};
    }

    private Map<String, Object> initProperties(List<Storage> storages) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("size", storages.size());

        return properties;
    }

    private Entity[] initEntities(long houseId, List<Storage> storages) {
        Entity[] entities = new Entity[storages.size()];
        for (int i = 0; i < storages.size(); ++i) {
            Storage storage = storages.get(i);

            HashMap<String, Object> properties = new HashMap<>();
            properties.put("house-id", houseId);
            properties.put("storage-id", storage.getId().getStorageId());
            properties.put("storage-name", storage.getStorageName());
            properties.put("storage-temperature", storage.getStorageTemperature());

            String storageUri = UriBuilderUtils.buildStorageUri(houseId, storage.getId().getStorageId());

            entities[i] = new Entity(
                    new String[]{"storage"},
                    new String[]{"item"},
                    properties,
                    null,
                    new Link[]{new Link(new String[]{"self"}, new String[]{"storage"}, storageUri)});
        }
        return entities;
    }

    private Action[] initActions(long houseId) {
        // Type
        String type = "application/json";

        // URIs
        String storagesUri = UriBuilderUtils.buildStoragesUri(houseId);

        // POST Storage
        Action postStorage = new Action(
                "add-storage",
                "Add Storage",
                Method.POST,
                storagesUri,
                type,
                new Field[]{
                        new Field("storage-name", Field.Type.text, null, "Name"),
                        new Field("storage-minimum-temperature", Field.Type.number, null, "Minimum Temperature"),
                        new Field("storage-maximum-temperature", Field.Type.number, null, "Maximum Temperature")
                }
        );

        return new Action[]{postStorage};
    }

    private Link[] initLinks(long houseId) {
        //URIs
        String houseUri = UriBuilderUtils.buildHouseUri(houseId);
        String storagesUri = UriBuilderUtils.buildStoragesUri(houseId);

        // Link-self
        Link self = new Link(new String[]{"self"}, new String[]{ENTITY_CLASS, "collection"}, storagesUri);
        //Link-related-house
        Link houseLink = new Link(new String[]{"related"}, new String[]{"house"}, houseUri);

        return new Link[]{self, houseLink};
    }
}
