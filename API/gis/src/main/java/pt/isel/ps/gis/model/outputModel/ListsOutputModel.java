package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.Collection;
import pt.isel.ps.gis.hypermedia.collectionPlusJson.components.subentities.*;
import pt.isel.ps.gis.model.List;
import pt.isel.ps.gis.utils.UriBuilderUtils;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListsOutputModel {

    @JsonProperty
    private final Collection collection;

    public ListsOutputModel(long houseId, java.util.List<List> lists) {
        this.collection = initCollection(houseId, lists);
    }

    private Collection initCollection(long houseId, java.util.List<List> lists) {
        //URIs
        String houseUri = UriBuilderUtils.buildHouseUri(houseId);
        String listsUri = UriBuilderUtils.buildListsUri(houseId);

        // Version
        String version = "1.0";

        // Link
        Link[] links = new Link[]{
                new Link("house", houseUri)
        };

        // Items
        Item[] items = mapItems(houseId, lists);

        Query[] queries = new Query[]{
                new Query(listsUri, "search", "Search by system or/and user or/and sharebale", new Data[]{
                        new Data("system", null, "System"),
                        new Data("user", null, "User"),
                        new Data("shareable", null, "Shareable")
                })
        };

        // Template
        Template template = new Template(
                new Data[]{
                        new Data("user-username", null, "Username"),
                        new Data("list-name", null, "Name"),
                        new Data("list-shareable", null, "Shareable")
                });

        return new Collection(version, listsUri, links, items, queries, template);
    }

    private Item[] mapItems(long houseId, java.util.List<List> lists) {
        int listsSize = lists.size();
        Item[] items = new Item[listsSize];
        for (int i = 0; i < listsSize; i++) {
            List list = lists.get(i);
            short listId = list.getId().getListId();
            if (list.getListType().equals("system")) {
                items[i] = new Item(
                        null,   // TODO check this
                        new Data[]{
                                new Data("house-id", houseId, "House ID"),
                                new Data("list-id", listId, "List ID"),
                                new Data("list-name", list.getListName(), "Name")
                        },
                        null
                );
            } else {
                items[i] = new Item(
                        UriBuilderUtils.buildListUri(houseId, listId),
                        new Data[]{
                                new Data("house-id", houseId, "House ID"),
                                new Data("list-id", listId, "List ID"),
                                new Data("list-name", list.getListName(), "Name"),
                                new Data("user-username", list.getUserlist().getUsersUsername(), "Username"),
                                new Data("list-shareable", list.getUserlist().getListShareable(), "Shareable")
                        },
                        new Link[]{
                                new Link("products-list", UriBuilderUtils.buildProductsListUri(houseId, listId))
                        }
                );
            }
        }
        return items;
    }
}
