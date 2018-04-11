package pt.isel.ps.gis.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "listproduct")
public class ListProduct {

    @EmbeddedId
    private ListProductId id;

    @Basic
    @Column(name = "listproduct_brand", length = 35)
    private String listproductBrand;

    @Basic
    @Column(name = "listproduct_quantity", nullable = false)
    private Short listproductQuantity;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "house_id", referencedColumnName = "house_id", nullable = false),
            @JoinColumn(name = "list_id", referencedColumnName = "list_id", nullable = false)
    })
    private List list;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = false),
            @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    })
    private Product product;

    public ListProductId getId() {
        return id;
    }

    public void setId(ListProductId id) {
        this.id = id;
    }

    public String getListproductBrand() {
        return listproductBrand;
    }

    public void setListproductBrand(String listproductBrand) {
        this.listproductBrand = listproductBrand;
    }

    public Short getListproductQuantity() {
        return listproductQuantity;
    }

    public void setListproductQuantity(Short listproductQuantity) {
        this.listproductQuantity = listproductQuantity;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ListProduct that = (ListProduct) obj;
        return Objects.equals(id, that.id) &&
                Objects.equals(listproductBrand, that.listproductBrand) &&
                Objects.equals(listproductQuantity, that.listproductQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, listproductBrand, listproductQuantity);
    }
}
