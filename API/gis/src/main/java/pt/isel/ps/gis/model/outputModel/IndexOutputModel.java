package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import pt.isel.ps.gis.hypermedia.EmptyObject;
import pt.isel.ps.gis.hypermedia.jsonHome.components.Api;
import pt.isel.ps.gis.hypermedia.jsonHome.components.members.Hints;
import pt.isel.ps.gis.hypermedia.jsonHome.components.members.Links;
import pt.isel.ps.gis.hypermedia.jsonHome.components.members.Method;
import pt.isel.ps.gis.hypermedia.jsonHome.components.members.ResourceObject;
import pt.isel.ps.gis.utils.UriBuilderUtils;

import java.util.HashMap;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"api", "resources"})
public class IndexOutputModel {

    @JsonProperty
    private final Api api;

    @JsonProperty
    private final HashMap<String, ResourceObject> resources;

    private HashMap<String, Object> applicationJsonFormat = new HashMap<>();

    public IndexOutputModel() {
        applicationJsonFormat.put("application/json", new EmptyObject());
        this.api = initApi();
        this.resources = initResources();
    }

    private Api initApi() {
        String title = "Gestão Inteligente de Stocks API";
        Links links = new Links(
                "https://github.com/isel42162/projeto",
                null,
                null
        );
        return new Api(title, links);
    }

    private HashMap<String, ResourceObject> initResources() {
        HashMap<String, ResourceObject> resources = new HashMap<>();
        resources.put("rel/house", createPostHouses());
        resources.put("rel/users", createPostUsers());
        resources.put("rel/user", createGetUsers());
        resources.put("rel/categories", createGetCategories());
        resources.put("rel/allergies", createGetAllergies());
        resources.put("rel/houses", createGetHouses());
        resources.put("rel/lists", createGetUserLists());
        return resources;
    }

    // POST /houses
    private ResourceObject createPostHouses() {
        return new ResourceObject(
                UriBuilderUtils.buildHousesUri(),
                null,
                null,
                new Hints(
                        new Method[]{Method.POST},
                        applicationJsonFormat,
                        new String[]{"application/json"},
                        null,
                        null
                )
        );
    }

    // POST /users
    private ResourceObject createPostUsers() {
        return new ResourceObject(
                UriBuilderUtils.buildUsersUri(),
                null,
                null,
                new Hints(
                        new Method[]{Method.POST},
                        applicationJsonFormat,
                        new String[]{"application/json"},
                        null,
                        null
                )
        );
    }

    // GET /users/{username}
    private ResourceObject createGetUsers() {
        HashMap<String, String> hrefVars = new HashMap<>();
        hrefVars.put("username", "String");

        return new ResourceObject(
                null,
                UriBuilderUtils.buildUserUriTemplate(),
                hrefVars,
                new Hints(
                        new Method[]{Method.GET},
                        applicationJsonFormat,
                        null,
                        null,
                        null
                )
        );
    }

    // GET /categories
    private ResourceObject createGetCategories() {
        return new ResourceObject(
                UriBuilderUtils.buildCategoriesUri(),
                null,
                null,
                new Hints(
                        new Method[]{Method.GET},
                        applicationJsonFormat,
                        null,
                        null,
                        null
                )
        );
    }

    // GET /allergies
    private ResourceObject createGetAllergies() {
        return new ResourceObject(
                UriBuilderUtils.buildAllergiesUri(),
                null,
                null,
                new Hints(
                        new Method[]{Method.GET},
                        applicationJsonFormat,
                        null,
                        null,
                        null
                )
        );
    }

    // GET /houses
    private ResourceObject createGetHouses() {
        HashMap<String, String> hrefVars = new HashMap<>();
        hrefVars.put("username", "String");

        return new ResourceObject(
                null,
                UriBuilderUtils.buildUserHousesUriTemplate(),
                hrefVars,
                new Hints(
                        new Method[]{Method.GET},
                        applicationJsonFormat,
                        null,
                        null,
                        null
                )
        );
    }

    // GET /lists
    private ResourceObject createGetUserLists() {
        HashMap<String, String> hrefVars = new HashMap<>();
        hrefVars.put("username", "String");

        return new ResourceObject(
                null,
                UriBuilderUtils.buildUserListsUriTemplate(),
                hrefVars,
                new Hints(
                        new Method[]{Method.GET},
                        applicationJsonFormat,
                        null,
                        null,
                        null
                )
        );

    }
}
