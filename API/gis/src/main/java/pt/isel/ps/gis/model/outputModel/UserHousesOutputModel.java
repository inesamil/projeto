package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.Collection;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Data;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Item;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Link;
import pt.isel.ps.gis.model.House;
import pt.isel.ps.gis.utils.UriBuilderUtils;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserHousesOutputModel {

    @JsonProperty
    private final Collection collection;

    public UserHousesOutputModel(String username, List<House> houses) {
        this.collection = initCollection(username, houses);
    }

    private Collection initCollection(String username, List<House> houses) {
        //URIs
        String userUri = UriBuilderUtils.buildUserUri(username);
        String userHousesUri = UriBuilderUtils.buildUserHousesUri(username);

        // Version
        String version = "1.0";

        // Link
        Link[] links = new Link[]{
                new Link("user", userUri)
        };

        // Items
        Item[] items = mapItems(houses);

        return new Collection(version, userHousesUri, links, items);
    }

    private Item[] mapItems(List<House> houses) {
        int housesSize = houses.size();
        Item[] items = new Item[housesSize];
        for (int i = 0; i < housesSize; i++) {
            House house = houses.get(i);
            long houseId = house.getHouseId();
            items[i] = new Item(
                    UriBuilderUtils.buildHouseUri(houseId),
                    new Data[]{
                            new Data("house-id", houseId, "ID"),
                            new Data("house-name", house.getHouseName(), "Name")
                    },
                    new Link[]{
                            new Link("movements", UriBuilderUtils.buildMovementsUri(houseId)),
                            new Link("items", UriBuilderUtils.buildStockItemsUri(houseId)),
                            new Link("household", UriBuilderUtils.buildHouseholdUri(houseId)),
                            new Link("house-allergies", UriBuilderUtils.buildHouseAllergiesUri(houseId)),
                            new Link("lists", UriBuilderUtils.buildListsUri(houseId)),
                            new Link("storages", UriBuilderUtils.buildStoragesUri(houseId))
                    }
            );
        }
        return items;
    }
}
