package pt.isel.ps.gis.model;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.utils.RestrictionsUtils;
import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "userlist")
public class UserList {

    private final static String TYPE = "user";

    /**
     * COLUNAS
     */
    @EmbeddedId
    private UserListId id;

    @Basic
    @Column(name = "users_username", length = RestrictionsUtils.USER_USERNAME_MAX_LENGTH, nullable = false)
    private String usersUsername;

    @Basic
    @Column(name = "list_shareable")
    private Boolean listShareable;

    /**
     * ASSOCIAÇÕES
     */
    @OneToOne
    @MapsId
    @JoinColumns({
            @JoinColumn(name = "house_id", referencedColumnName = "house_id", nullable = false),
            @JoinColumn(name = "list_id", referencedColumnName = "list_id", nullable = false)
    })
    private List list;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_username", referencedColumnName = "users_username", nullable = false, insertable = false, updatable = false)
    private Users usersByUsersUsername;

    /**
     * CONSTRUTORES
     */
    protected UserList() {
    }

    public UserList(Long houseId, String listName, String usersUsername, Boolean listShareable) throws EntityException {
        setId(houseId);
        this.list = new List(houseId, listName, TYPE);
        setUsersUsername(usersUsername);
        setListShareable(listShareable);
    }

    public UserList(Long houseId, Short listId, String listName, String usersUsername, Boolean listShareable) throws EntityException {
        setId(houseId, listId);
        this.list = new List(houseId, listId, listName, TYPE);
        setUsersUsername(usersUsername);
        setListShareable(listShareable);
    }

    /**
     * GETTERS E SETTERS
     */
    public UserListId getId() {
        return id;
    }

    public void setId(UserListId listId) {
        this.id = listId;
    }

    public void setId(Long houseId) throws EntityException {
        setId(new UserListId(houseId));
    }

    public void setId(Long houseId, Short listId) throws EntityException {
        setId(new UserListId(houseId, listId));
    }

    public String getUsersUsername() {
        return usersUsername;
    }

    public void setUsersUsername(String usersUsername) throws EntityException {
        ValidationsUtils.validateUserUsername(usersUsername);
        this.usersUsername = usersUsername;
    }

    public Boolean getListShareable() {
        return listShareable;
    }

    public void setListShareable(Boolean listShareable) throws EntityException {
        ValidationsUtils.validateListShareable(listShareable);
        this.listShareable = listShareable;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public Users getUsersByUsersUsername() {
        return usersByUsersUsername;
    }

    public void setUsersByUsersUsername(Users usersByUsersUsername) {
        this.usersByUsersUsername = usersByUsersUsername;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UserList that = (UserList) obj;
        return Objects.equals(id, that.id) &&
                Objects.equals(usersUsername, that.usersUsername) &&
                Objects.equals(listShareable, that.listShareable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, usersUsername, listShareable);
    }
}
