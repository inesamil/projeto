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
        resources.put("rel/user", createGetPutUsers());
        resources.put("rel/categories", createGetCategories());
        resources.put("rel/allergies", createGetAllergies());
        resources.put("rel/houses", createGetHouses());
        return resources;
    }

    // POST /houses
    private ResourceObject createPostHouses() {
        return new ResourceObject(
                "/v1/houses",
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

    // GET | PUT /users/{username}
    private ResourceObject createGetPutUsers() {
        HashMap<String, String> hrefVars = new HashMap<>();
        hrefVars.put("username", "String");

        return new ResourceObject(
                null,
                "http://10.0.2.2:8081/v1/users/{username}",
                hrefVars,
                new Hints(
                        new Method[]{Method.GET, Method.PUT},
                        applicationJsonFormat,
                        null,
                        new String[]{"application/json"},
                        null
                )
        );
    }

    // GET /categories
    private ResourceObject createGetCategories() {
        return new ResourceObject(
                "/v1/categories",
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
                "/v1/allergies",
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

    private ResourceObject createGetHouses() {
        HashMap<String, String> hrefVars = new HashMap<>();
        hrefVars.put("username", "String");

        return new ResourceObject(
                null,
                "http://10.0.2.2:8081/v1/users/{username}/houses",
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
