package pt.isel.ps.gis.dal.bdModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

@Entity(name = "List")
@Table(name = "List")
@Inheritance(strategy = InheritanceType.JOINED)
public class List {

    @EmbeddedId
    private ListId id;

    @Column(name = "list_name", length = 35, nullable = false)
    private String name;

    @Column(name = "list_type", length = 7, nullable = false)
    private String type;

    @OneToMany(mappedBy = "list",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private java.util.List<ListProduct> products = new ArrayList<>();

    protected List() { }

    public List(ListId id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public void addProduct(Product product) {
        ListProduct listProduct = new ListProduct(this, product);
        products.add(listProduct);
    }

    public void removeProduct(Product product) {
        for (ListProduct listProduct : products) {
            if(listProduct.getList().equals(this) && listProduct.getProduct().equals(product)) {
                products.remove(listProduct);
                listProduct.setList(null);
                listProduct.setProduct(null);
            }
        }
    }

    public ListId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}

@Embeddable
class ListId implements Serializable {

    @Column(name = "house_id", nullable = false)
    private long houseId;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "list_id", nullable = false)
    private short listId;

    protected ListId() {}

    public ListId(long houseId, short listId) {
        this.houseId = houseId;
        this.listId = listId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof ListId))
            return false;
        ListId other = (ListId) obj;
        return Objects.equals(houseId, other.houseId) &&
                Objects.equals(listId, other.listId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(houseId, listId);
    }

    public long getHouseId() {
        return houseId;
    }

    public short getListId() {
        return listId;
    }
}
