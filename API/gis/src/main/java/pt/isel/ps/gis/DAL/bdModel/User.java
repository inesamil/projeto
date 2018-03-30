package pt.isel.ps.gis.DAL.bdModel;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "User")
public class User {

    @Id
    @Column(name = "user_username", length = 30, nullable = false)
    private String username;

    @Column(name = "user_email", length = 254, nullable = false, unique = true)
    private String email;

    @Column(name = "user_age", nullable = false)
    private short age;

    @Column(name = "user_name", length = 70, nullable = false)
    private String name;

    @Column(name = "user_password", length = 50, nullable = false)
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<UserList> lists;

    protected User() {
    }

    public User(String username, String email, short age, String name, String password) {
        this.username = username;
        this.email = email;
        this.age = age;
        this.name = name;
        this.password = password;
    }
}
