package pt.isel.ps.gis.models;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "list")
public class List {

    @EmbeddedId
    private ListId id;

    @Basic
    @Column(name = "list_name")
    private String listName;

    @Basic
    @Column(name = "list_type")
    private String listType;

    @ManyToOne
    @JoinColumn(name = "house_id", referencedColumnName = "house_id", nullable = false)
    private House houseByHouseId;

    @OneToMany(mappedBy = "list")
    private Collection<ListProduct> listproducts;

    @OneToOne(mappedBy = "list")
    private SystemList systemlist;

    @OneToOne(mappedBy = "list")
    private UserList userlist;

    public ListId getId() {
        return id;
    }

    public void setId(ListId id) {
        this.id = id;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getListType() {
        return listType;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }

    public House getHouseByHouseId() {
        return houseByHouseId;
    }

    public void setHouseByHouseId(House houseByHouseId) {
        this.houseByHouseId = houseByHouseId;
    }

    public Collection<ListProduct> getListproducts() {
        return listproducts;
    }

    public void setListproducts(Collection<ListProduct> listproducts) {
        this.listproducts = listproducts;
    }

    public SystemList getSystemlist() {
        return systemlist;
    }

    public void setSystemlist(SystemList systemlist) {
        this.systemlist = systemlist;
    }

    public UserList getUserlist() {
        return userlist;
    }

    public void setUserlist(UserList userlist) {
        this.userlist = userlist;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        List that = (List) obj;
        return Objects.equals(id, that.id) &&
                Objects.equals(listName, that.listName) &&
                Objects.equals(listType, that.listType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, listName, listType);
    }
}
