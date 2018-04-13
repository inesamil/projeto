package pt.isel.ps.gis.models;

import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductId implements Serializable {

    /**
     * COLUNAS
     */
    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    /**
     * CONSTRUTORES
     */
    protected ProductId() {}

    public ProductId(Integer categoryId, Integer productId) throws IllegalArgumentException {
        this.categoryId = categoryId;
        this.productId = productId;
    }

    /**
     * GETTERS E SETTERS
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) throws IllegalArgumentException {
        ValidationsUtils.validateCategoryId(categoryId);
        this.categoryId = categoryId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) throws IllegalArgumentException {
        ValidationsUtils.validateProductId(productId);
        this.productId = productId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ProductId that = (ProductId) obj;
        return Objects.equals(categoryId, that.categoryId) &&
                Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, productId);
    }
}
