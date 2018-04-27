package pt.isel.ps.gis.model;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.utils.RestrictionsUtils;
import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "users")
public class Users {

    /**
     * COLUNAS
     */
    @Id
    @Column(name = "users_username", length = RestrictionsUtils.USER_USERNAME_MAX_LENGTH, nullable = false)
    private String usersUsername;

    @Basic
    @Column(name = "users_email", length = RestrictionsUtils.USER_EMAIL_MAX_LENGTH, nullable = false, unique = true)
    private String usersEmail;

    @Basic
    @Column(name = "users_age", nullable = false)
    private Short usersAge;

    @Basic
    @Column(name = "users_name", length = RestrictionsUtils.USER_NAME_MAX_LENGTH, nullable = false)
    private String usersName;

    @Basic
    @Column(name = "users_password", length = RestrictionsUtils.USER_PASSWORD_MAX_LENGTH, nullable = false)
    private String usersPassword;

    /**
     * ASSOCIAÇÕES
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usersByUsersUsername")
    private Collection<UserHouse> userhousesByUsersUsername;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usersByUsersUsername")
    private Collection<UserList> userlistsByUsersUsername;

    /**
     * CONSTRUTORES
     */
    protected Users() {
    }

    public Users(String username, String email, Short age, String name, String password) throws EntityException {
        setUsersUsername(username);
        setUsersEmail(email);
        setUsersAge(age);
        setUsersName(name);
        setUsersPassword(password);
    }

    /**
     * GETTERS E SETTERS
     */
    public String getUsersUsername() {
        return usersUsername;
    }

    public void setUsersUsername(String usersUsername) throws EntityException {
        ValidationsUtils.validateUserUsername(usersUsername);
        this.usersUsername = usersUsername;
    }

    public String getUsersEmail() {
        return usersEmail;
    }

    public void setUsersEmail(String usersEmail) throws EntityException {
        ValidationsUtils.validateUserEmail(usersEmail);
        this.usersEmail = usersEmail;
    }

    public Short getUsersAge() {
        return usersAge;
    }

    public void setUsersAge(Short usersAge) throws EntityException {
        ValidationsUtils.validateUserAge(usersAge);
        this.usersAge = usersAge;
    }

    public String getUsersName() {
        return usersName;
    }

    public void setUsersName(String usersName) throws EntityException {
        ValidationsUtils.validateUserName(usersName);
        this.usersName = usersName;
    }

    public String getUsersPassword() {
        return usersPassword;
    }

    public void setUsersPassword(String usersPassword) throws EntityException {
        ValidationsUtils.validateUserPassword(usersPassword);
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
