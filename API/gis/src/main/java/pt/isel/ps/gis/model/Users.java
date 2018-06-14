package pt.isel.ps.gis.model;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.utils.RestrictionsUtils;
import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "users")
public class Users {

    /**
     * COLUNAS
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id", nullable = false)
    private Long usersId;

    @Basic
    @Column(name = "users_username", length = RestrictionsUtils.USER_USERNAME_MAX_LENGTH, nullable = false, unique = true)
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
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "usersByUsersId"
    )
    private Collection<UserHouse> userhousesByUsersId = new ArrayList<>();

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "usersByUsersId"
    )
    private Collection<UserList> userlistsByUsersId = new ArrayList<>();

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "usersByUsersId"
    )
    private Collection<UserRole> usersrolesByUsersId = new ArrayList<>();

    /**
     * CONSTRUTORES
     */
    protected Users() {
    }

    public Users(Long id, String username, String email, Short age, String name, String password) throws EntityException {
        setUsersId(id);
        setUsersUsername(username);
        setUsersEmail(email);
        setUsersAge(age);
        setUsersName(name);
        setUsersPassword(password);
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
    public Long getUsersId() {
        return usersId;
    }

    public void setUsersId(Long usersId) throws EntityException {
        ValidationsUtils.validateUserId(usersId);
        this.usersId = usersId;
    }

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

    public Collection<UserHouse> getUserhousesByUsersId() {
        return userhousesByUsersId;
    }

    public void setUserhousesByUsersId(Collection<UserHouse> userhousesByUsersId) {
        this.userhousesByUsersId = userhousesByUsersId;
    }

    public Collection<UserList> getUserlistsByUsersId() {
        return userlistsByUsersId;
    }

    public void setUserlistsByUsersId(Collection<UserList> userlistsByUsersId) {
        this.userlistsByUsersId = userlistsByUsersId;
    }

    public Collection<UserRole> getUsersrolesByUsersId() {
        return usersrolesByUsersId;
    }

    public void setUsersrolesByUsersId(Collection<UserRole> usersrolesByUsersId) {
        this.usersrolesByUsersId = usersrolesByUsersId;
    }

    public void addUserHouse(UserHouse userHouse) {
        userhousesByUsersId.add(userHouse);
        userHouse.setUsersByUsersId(this);
    }

    public void removeUserHouse(UserHouse userHouse) {
        userhousesByUsersId.remove(userHouse);
        userHouse.setUsersByUsersId(null);
    }

    public void addUserList(UserList userList) {
        userlistsByUsersId.add(userList);
        userList.setUsersByUsersId(this);
    }

    public void removeUserlist(UserList userList) {
        userlistsByUsersId.remove(userList);
        userList.setUsersByUsersId(null);
    }

    public void addUserRole(UserRole userRole) {
        usersrolesByUsersId.add(userRole);
        userRole.setUsersByUsersId(this);
    }

    public void removeUserRole(UserRole userRole) {
        usersrolesByUsersId.remove(userRole);
        userRole.setUsersByUsersId(null);
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
