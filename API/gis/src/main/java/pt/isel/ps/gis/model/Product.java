package pt.isel.ps.gis.model;

import pt.isel.ps.gis.exceptions.EntityException;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Basic
    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

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
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private Collection<ListProduct> listproducts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = false, insertable = false, updatable = false)
    private Category categoryByCategoryId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private Collection<StockItem> stockitems;

    /**
     * CONSTRUTORES
     */
    protected Product() {
    }

    private Product(Integer categoryId, String productName, Boolean productEdible, Short productShelflife, String productShelflifeTimeunit) throws EntityException {
        setCategoryId(categoryId);
        setProductName(productName);
        setProductEdible(productEdible);
        setProductShelflife(productShelflife);
        setProductShelflifetimeunit(productShelflifeTimeunit);
    }

    public Product(Integer productId, Integer categoryId, String productName, Boolean productEdible, Short productShelflife, String productShelflifetimeunit) throws EntityException {
        this(categoryId, productName, productEdible, productShelflife, productShelflifetimeunit);
        setProductId(productId);
    }

    /**
     * GETTERS E SETTERS
     */

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) throws EntityException {
        ValidationsUtils.validateProductId(productId);
        this.productId = productId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) throws EntityException {
        ValidationsUtils.validateCategoryId(categoryId);
        this.categoryId = categoryId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) throws EntityException {
        ValidationsUtils.validateProductName(productName);
        this.productName = productName;
    }

    public Boolean getProductEdible() {
        return productEdible;
    }

    public void setProductEdible(Boolean productEdible) throws EntityException {
        ValidationsUtils.validateProductEdible(productEdible);
        this.productEdible = productEdible;
    }

    public Short getProductShelflife() {
        return productShelflife;
    }

    public void setProductShelflife(Short productShelflife) throws EntityException {
        ValidationsUtils.validateProductShelflife(productShelflife);
        this.productShelflife = productShelflife;
    }

    public String getProductShelflifetimeunit() {
        return productShelflifetimeunit;
    }

    public void setProductShelflifetimeunit(String productShelflifetimeunit) throws EntityException {
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
        return Objects.equals(productId, that.productId) &&
                Objects.equals(categoryId, that.categoryId) &&
                Objects.equals(productName, that.productName) &&
                Objects.equals(productEdible, that.productEdible) &&
                Objects.equals(productShelflife, that.productShelflife) &&
                Objects.equals(productShelflifetimeunit, that.productShelflifetimeunit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, categoryId, productName, productEdible, productShelflife, productShelflifetimeunit);
    }
}
