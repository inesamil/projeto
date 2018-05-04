package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.Collection;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Data;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Item;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.Link;
import pt.isel.ps.gis.model.Users;
import pt.isel.ps.gis.utils.UriBuilderUtils;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HouseholdOutputModel {

    @JsonProperty
    private final Collection collection;

    public HouseholdOutputModel(long houseId, List<Users> users) {
        this.collection = initCollection(houseId, users);
    }

    private Collection initCollection(long houseId, List<Users> users) {
        //URIs
        String houseUri = UriBuilderUtils.buildHouseUri(houseId);
        String householdUri = UriBuilderUtils.buildHouseholdUri(houseId);

        // Version
        String version = "1.0";

        // Link
        Link[] links = new Link[]{
                new Link("house", houseUri)
        };

        // Items
        Item[] items = mapItems(houseId, users);

        return new Collection(version, householdUri, links, items, null);
    }

    private Item[] mapItems(long houseId, List<Users> users) {
        int usersSize = users.size();
        Item[] items = new Item[usersSize];
        for (int i = 0; i < usersSize; i++) {
            Users user = users.get(i);
            String username = user.getUsersUsername();
            items[i] = new Item(
                    UriBuilderUtils.buildUserUri(username),
                    new Data[]{
                            new Data("house-id", houseId, "House ID"),
                            new Data("user-username", username, "Username"),
                            new Data("user-name", user.getUsersName(), "Name"),
                            new Data("user-email", user.getUsersEmail(), "Email"),
                            new Data("user-age", user.getUsersAge(), "Age")
                    },
                    new Link[]{
                            new Link("index", UriBuilderUtils.buildIndexUri()),
                            new Link("user-houses", UriBuilderUtils.buildUserHousesUri(username))
                    }
            );
        }
        return items;
    }
}
