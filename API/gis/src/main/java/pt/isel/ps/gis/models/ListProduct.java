package pt.isel.ps.gis.models;

import pt.isel.ps.gis.exceptions.ModelException;
import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "listproduct")
public class ListProduct {

    /**
     * COLUNAS
     */
    @EmbeddedId
    private ListProductId id;

    @Basic
    @Column(name = "listproduct_brand", length = 35)
    private String listproductBrand;

    @Basic
    @Column(name = "listproduct_quantity", nullable = false)
    private Short listproductQuantity;

    /**
     * ASSOCIAÇÕES
     */
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "house_id", referencedColumnName = "house_id", nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "list_id", referencedColumnName = "list_id", nullable = false, insertable = false, updatable = false)
    })
    private List list;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false, insertable = false, updatable = false)
    })
    private Product product;

    /**
     * CONSTRUTORES
     */
    protected ListProduct() {
    }

    public ListProduct(Long houseId, Short listId, Integer categoryId, Integer productId, String productBrand, Short productQuantity) throws ModelException {
        setId(houseId, listId, categoryId, productId);
        setListproductBrand(productBrand);
        setListproductQuantity(productQuantity);
    }

    /**
     * GETTERS E SETTERS
     */
    public ListProductId getId() {
        return id;
    }

    public void setId(ListProductId id) {
        this.id = id;
    }

    private void setId(Long houseId, Short listId, Integer categoryId, Integer productId) throws ModelException {
        setId(new ListProductId(houseId, listId, categoryId, productId));
    }

    public String getListproductBrand() {
        return listproductBrand;
    }

    public void setListproductBrand(String listproductBrand) throws ModelException {
        ValidationsUtils.validateListProductBrand(listproductBrand);
        this.listproductBrand = listproductBrand;
    }

    public Short getListproductQuantity() {
        return listproductQuantity;
    }

    public void setListproductQuantity(Short listproductQuantity) throws ModelException {
        ValidationsUtils.validateListProductQuantity(listproductQuantity);
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
