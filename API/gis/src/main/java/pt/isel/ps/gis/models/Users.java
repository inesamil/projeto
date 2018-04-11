package pt.isel.ps.gis.models;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @Column(name = "users_username")
    private String usersUsername;

    @Basic
    @Column(name = "users_email")
    private String usersEmail;

    @Basic
    @Column(name = "users_age")
    private Short usersAge;

    @Basic
    @Column(name = "users_name")
    private String usersName;

    @Basic
    @Column(name = "users_password")
    private String usersPassword;

    @OneToMany(mappedBy = "usersByUsersUsername")
    private Collection<UserHouse> userhousesByUsersUsername;

    @OneToMany(mappedBy = "usersByUsersUsername")
    private Collection<UserList> userlistsByUsersUsername;

    public String getUsersUsername() {
        return usersUsername;
    }

    public void setUsersUsername(String usersUsername) {
        this.usersUsername = usersUsername;
    }

    public String getUsersEmail() {
        return usersEmail;
    }

    public void setUsersEmail(String usersEmail) {
        this.usersEmail = usersEmail;
    }

    public Short getUsersAge() {
        return usersAge;
    }

    public void setUsersAge(Short usersAge) {
        this.usersAge = usersAge;
    }

    public String getUsersName() {
        return usersName;
    }

    public void setUsersName(String usersName) {
        this.usersName = usersName;
    }

    public String getUsersPassword() {
        return usersPassword;
    }

    public void setUsersPassword(String usersPassword) {
        this.usersPassword = usersPassword;
    }

    public Collection<UserHouse> getUserhousesByUsersUsername() {
        return userhousesByUsersUsername;
    }

    public void setUserhousesByUsersUsername(Collection<UserHouse> userhousesByUsersUsername) {
        this.userhousesByUsersUsername = userhousesByUsersUsername;
    }

    public Collection<UserList> getUserlistsByUsersUsername() {
        return userlistsByUsersUsername;
    }

    public void setUserlistsByUsersUsername(Collection<UserList> userlistsByUsersUsername) {
        this.userlistsByUsersUsername = userlistsByUsersUsername;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Users that = (Users) obj;
        return Objects.equals(usersUsername, that.usersUsername) &&
                Objects.equals(usersEmail, that.usersEmail) &&
                Objects.equals(usersAge, that.usersAge) &&
                Objects.equals(usersName, that.usersName) &&
                Objects.equals(usersPassword, that.usersPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usersUsername, usersEmail, usersAge, usersName, usersPassword);
    }
}
