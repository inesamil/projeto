package pt.isel.ps.gis.models;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "product")
public class Product {

    @EmbeddedId
    private ProductId id;

    @Basic
    @Column(name = "product_name")
    private String productName;

    @Basic
    @Column(name = "product_edible")
    private Boolean productEdible;

    @Basic
    @Column(name = "product_shelflife")
    private Short productShelflife;

    @Basic
    @Column(name = "product_shelflifetimeunit")
    private String productShelflifetimeunit;

    @OneToMany(mappedBy = "product")
    private Collection<ListProduct> listproducts;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = false)
    private Category categoryByCategoryId;

    @OneToMany(mappedBy = "product")
    private Collection<StockItem> stockitems;

    public ProductId getId() {
        return id;
    }

    public void setId(ProductId id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Boolean getProductEdible() {
        return productEdible;
    }

    public void setProductEdible(Boolean productEdible) {
        this.productEdible = productEdible;
    }

    public Short getProductShelflife() {
        return productShelflife;
    }

    public void setProductShelflife(Short productShelflife) {
        this.productShelflife = productShelflife;
    }

    public String getProductShelflifetimeunit() {
        return productShelflifetimeunit;
    }

    public void setProductShelflifetimeunit(String productShelflifetimeunit) {
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
