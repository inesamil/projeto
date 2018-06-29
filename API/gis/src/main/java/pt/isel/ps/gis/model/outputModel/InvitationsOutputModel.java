package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import pt.isel.ps.gis.hypermedia.siren.components.subentities.*;
import pt.isel.ps.gis.model.Invitation;
import pt.isel.ps.gis.utils.UriBuilderUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"class", "properties", "entities", "actions", "links"})
public class InvitationsOutputModel {

    private final static String ENTITY_CLASS = "invitations";

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

    public InvitationsOutputModel(List<Invitation> invitations) {
        this.klass = initKlass();
        this.properties = initProperties(invitations);
        this.entities = initEntities(invitations);
        this.actions = initActions();
        this.links = initLinks();
    }

    private String[] initKlass() {
        return new String[]{ENTITY_CLASS, "collection"};
    }

    private Map<String,Object> initProperties(List<Invitation> invitations) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("size", invitations.size());

        return properties;
    }

    private Entity[] initEntities(List<Invitation> invitations) {
        Entity[] entities = new Entity[invitations.size()];
        for (int i = 0; i < invitations.size(); ++i) {
            Invitation invitation = invitations.get(i);

            Long house_Id = invitation.getId().getHouseId();
            String users_username = invitation.getUsersByUsersId().getUsersUsername();

            HashMap<String, Object> properties = new HashMap<>();
            properties.put("house-id", house_Id);
            properties.put("house-name", invitation.getHouseByHouseId().getHouseName());
            properties.put("user-username", users_username);

            String invitationUri = UriBuilderUtils.buildInvitationUri(house_Id, users_username);

            entities[i] = new Entity(
                    new String[]{"invitation"},
                    new String[]{"item"},
                    properties,
                    new Action[]{
                            new Action(
                                    "update-invitation",
                                    "Update Invitation",
                                    Method.PUT,
                                    invitationUri,
                                    "application/json",
                                    new Field[]{
                                            new Field("accept", Field.Type.bool, null, "Accept")
                                    }
                            )
                    },
                    null);
        }
        return entities;
    }

    private Action[] initActions() {
        // Type
        String type = "application/json";

        // URIs
        String invitationsUri = UriBuilderUtils.buildInvitationsUri();

        // POST Invitation
        Action postInvitation = new Action(
                "add-invitation",
                "Add Invitation",
                Method.POST,
                invitationsUri,
                type,
                new Field[]{
                        new Field("house-id", Field.Type.number, null, "House Id"),
                        new Field("user-username", Field.Type.text, null, "User Username")
                }
        );

        return new Action[]{postInvitation};
    }

    private Link[] initLinks() {
        //URIs
        String indexUri = UriBuilderUtils.buildIndexUri();
        String invitationsUri = UriBuilderUtils.buildInvitationsUri();

        //Link-index
        Link indexLink = new Link(new String[]{"index"}, new String[]{"index"}, indexUri);
        //Link-self
        Link self = new Link(new String[]{"self"}, new String[]{ENTITY_CLASS, "collection"}, invitationsUri);
        //Link-houses
        Link houses = new Link(new String[]{"related"}, new String[]{"houses", "collection"}, UriBuilderUtils.buildHousesUri());

        return new Link[]{self, indexLink};
    }
}
