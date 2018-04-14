package pt.isel.ps.gis.model;

import pt.isel.ps.gis.exceptions.ModelException;
import pt.isel.ps.gis.utils.RestrictionsUtils;
import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "product")
public class Product {

    /**
     * COLUNAS
     */
    @EmbeddedId
    private ProductId id;

    @Basic
    @Column(name = "product_name", length = RestrictionsUtils.PRODUCT_NAME_MAX_LENGTH, nullable = false)
    private String productName;

    @Basic
    @Column(name = "product_edible", nullable = false)
    private Boolean productEdible;

    @Basic
    @Column(name = "product_shelflife", nullable = false)
    private Short productShelflife;

    @Basic
    @Column(name = "product_shelflifetimeunit", length = RestrictionsUtils.PRODUCT_SHELFLIFETIMEUNIT_MAX_LENGTH, nullable = false)
    private String productShelflifetimeunit;

    /**
     * ASSOCIAÇÕES
     */
    @OneToMany(mappedBy = "product")
    private Collection<ListProduct> listproducts;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = false, insertable = false, updatable = false)
    private Category categoryByCategoryId;

    @OneToMany(mappedBy = "product")
    private Collection<StockItem> stockitems;

    /**
     * CONSTRUTORES
     */
    protected Product() {
    }

    public Product(String productName, Boolean productEdible, Short productShelflife, String productShelflifeTimeunit) throws ModelException {
        setProductName(productName);
        setProductEdible(productEdible);
        setProductShelflife(productShelflife);
        setProductShelflifetimeunit(productShelflifeTimeunit);
    }

    public Product(Integer categoryId, Integer productId, String productName, Boolean productEdible, Short productShelflife, String productShelflifetimeunit) throws ModelException {
        this(productName, productEdible, productShelflife, productShelflifetimeunit);
        setId(categoryId, productId);
    }

    /**
     * GETTERS E SETTERS
     */

    public ProductId getId() {
        return id;
    }

    private void setId(ProductId id) {
        this.id = id;
    }


    public void setId(Integer categoryId, Integer productId) throws ModelException {
        setId(new ProductId(categoryId, productId));
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) throws ModelException {
        ValidationsUtils.validateProductName(productName);
        this.productName = productName;
    }

    public Boolean getProductEdible() {
        return productEdible;
    }

    public void setProductEdible(Boolean productEdible) throws ModelException {
        ValidationsUtils.validateProductEdible(productEdible);
        this.productEdible = productEdible;
    }

    public Short getProductShelflife() {
        return productShelflife;
    }

    public void setProductShelflife(Short productShelflife) throws ModelException {
        ValidationsUtils.validateProductShelflife(productShelflife);
        this.productShelflife = productShelflife;
    }

    public String getProductShelflifetimeunit() {
        return productShelflifetimeunit;
    }

    public void setProductShelflifetimeunit(String productShelflifetimeunit) throws ModelException {
        ValidationsUtils.validateProductShelflifeTimeunit(productShelflifetimeunit);
        this.productShelflifetimeunit = productShelflifetimeunit;
    }

    public Collection<ListProduct> getListproducts() {
        return listproducts;
    }

    public void setListproducts(Collection<ListProduct> listproducts) {
        this.listproducts = listproducts;
    }

    public Category getCategoryByCategoryId() {
        return categoryByCategoryId;
    }

    public void setCategoryByCategoryId(Category categoryByCategoryId) {
        this.categoryByCategoryId = categoryByCategoryId;
    }

    public Collection<StockItem> getStockitems() {
        return stockitems;
    }

    public void setStockitems(Collection<StockItem> stockitems) {
        this.stockitems = stockitems;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product that = (Product) obj;
        return Objects.equals(id, that.id) &&
                Objects.equals(productName, that.productName) &&
                Objects.equals(productEdible, that.productEdible) &&
                Objects.equals(productShelflife, that.productShelflife) &&
                Objects.equals(productShelflifetimeunit, that.productShelflifetimeunit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, productEdible, productShelflife, productShelflifetimeunit);
    }
}
