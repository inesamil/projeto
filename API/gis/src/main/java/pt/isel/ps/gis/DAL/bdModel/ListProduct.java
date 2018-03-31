package pt.isel.ps.gis.DAL.bdModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "ListProduct")
@Table(name = "ListProduct")
public class ListProduct {

    @EmbeddedId
    private ListProductId id;

    @Column(name = "listProduct_brand", length = 35)
    private String brand;

    @Column(name = "listProduct_quantity", nullable = false)
    private short quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("id")
    private List list;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("id")
    private Product product;

    protected ListProduct(List list, Product product) {
        ListId listId = list.getId();
        ProductId productId = product.getId();
        this.id = new ListProductId(listId.getHouseId(), listId.getListId(), productId.getCategoryId(), productId.getProductId());
    }

    public ListProductId getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public short getQuantity() {
        return quantity;
    }

    public void setQuantity(short quantity) {
        this.quantity = quantity;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

@Embeddable
class ListProductId implements Serializable {

    @Column(name = "house_id", nullable = false)
    private long houseId;

    @Column(name = "list_id", nullable = false)
    private short listId;

    @Column(name = "category_id", nullable = false)
    private int categoryId;

    @Column(name = "product_id", nullable = false)
    private int productId;

    protected ListProductId() {}

    public ListProductId(long houseId, short listId, int categoryId, int productId) {
        this.houseId = houseId;
        this.listId = listId;
        this.categoryId = categoryId;
        this.productId = productId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof ListProductId))
            return false;
        ListProductId other = (ListProductId) obj;
        return Objects.equals(houseId, other.houseId) &&
                Objects.equals(listId, other.listId) &&
                Objects.equals(categoryId, other.categoryId) &&
                Objects.equals(productId, other.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(houseId, listId, categoryId, productId);
    }
}
