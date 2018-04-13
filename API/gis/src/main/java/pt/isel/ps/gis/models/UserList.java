package pt.isel.ps.gis.models;

import pt.isel.ps.gis.exceptions.ModelException;
import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "userlist")
public class UserList {

    /**
     * COLUNAS
     */
    @EmbeddedId
    private UserListId id;

    @Basic
    @Column(name = "users_username", length = 30, nullable = false)
    private String usersUsername;

    @Basic
    @Column(name = "list_shareable")
    private Boolean listShareable;

    /**
     * ASSOCIAÇÕES
     */
    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "house_id", referencedColumnName = "house_id", nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "list_id", referencedColumnName = "list_id", nullable = false, insertable = false, updatable = false)
    })
    private List list;

    @ManyToOne
    @JoinColumn(name = "users_username", referencedColumnName = "users_username", nullable = false, insertable = false, updatable = false)
    private Users usersByUsersUsername;

    /**
     * CONSTRUTORES
     */
    protected UserList() {}

    public UserList(Long houseId, Short listId, String usersUsername, Boolean listShareable) throws ModelException {
        setId(houseId, listId);
        setUsersUsername(usersUsername);
        setListShareable(listShareable);
    }

    /**
     * GETTERS E SETTERS
     */
    public UserListId getId() {
        return id;
    }

    private void setId(UserListId id) {
        this.id = id;
    }

    public void setId(Long houseId, Short listId) {
        setId(new UserListId(houseId, listId));
    }

    public String getUsersUsername() {
        return usersUsername;
    }

    public void setUsersUsername(String usersUsername) throws ModelException {
        ValidationsUtils.validateUserUsername(usersUsername);
        this.usersUsername = usersUsername;
    }

    public Boolean getListShareable() {
        return listShareable;
    }

    public void setListShareable(Boolean listShareable) throws ModelException {
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
