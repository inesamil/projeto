package pt.isel.ps.gis.DAL.bdModel;

import javax.persistence.*;

@Entity
@Table(name = "UserList")
public class UserList extends List {

    private static final String USER_LIST_TYPE = "user";

    // @Column(name = "user_username", length = 30, nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_username")
    private User user;

    @Column(name = "list_shareable")
    private boolean shareable;

    public UserList(ListId id, String name, User user, boolean shareable) {
        super(id, name, USER_LIST_TYPE);
        this.user = user;
        this.shareable = shareable;
    }
}
