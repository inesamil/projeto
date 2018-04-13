package pt.isel.ps.gis.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "userlist")
public class UserList {

    @EmbeddedId
    private UserListId id;

    @Basic
    @Column(name = "users_username", length = 30, nullable = false)
    private String usersUsername;

    @Basic
    @Column(name = "list_shareable")
    private Boolean listShareable;

    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "house_id", referencedColumnName = "house_id", nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "list_id", referencedColumnName = "list_id", nullable = false, insertable = false, updatable = false)
    })
    private List list;

    @ManyToOne
    @JoinColumn(name = "users_username", referencedColumnName = "users_username", nullable = false, insertable = false, updatable = false)
    private Users usersByUsersUsername;

    public UserListId getId() {
        return id;
    }

    public void setId(UserListId id) {
        this.id = id;
    }

    public String getUsersUsername() {
        return usersUsername;
    }

    public void setUsersUsername(String usersUsername) {
        this.usersUsername = usersUsername;
    }

    public Boolean getListShareable() {
        return listShareable;
    }

    public void setListShareable(Boolean listShareable) {
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
