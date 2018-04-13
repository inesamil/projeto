package pt.isel.ps.gis.models;

import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ListProductId implements Serializable {

    /**
     * COLUNAS
     */
    @Column(name = "house_id", nullable = false)
    private Long houseId;

    @Column(name = "list_id", nullable = false)
    private Short listId;

    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    /**
     * CONSTRUTORES
     */
    protected ListProductId() {
    }

    public ListProductId(Long houseId, Short listId, Integer categoryId, Integer productId) throws IllegalArgumentException {
        setHouseId(houseId);
        this.listId = listId;
        this.categoryId = categoryId;
        this.productId = productId;
    }

    /**
     * GETTERS E SETTERS
     */
    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) throws IllegalArgumentException {
        ValidationsUtils.validateHouseId(houseId);
        this.houseId = houseId;
    }

    public Short getListId() {
        return listId;
    }

    public void setListId(Short listId) throws IllegalArgumentException {
        ValidationsUtils.validateListId(listId);
        this.listId = listId;
    }

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
        ListProductId that = (ListProductId) obj;
        return Objects.equals(houseId, that.houseId) &&
                Objects.equals(listId, that.listId) &&
                Objects.equals(categoryId, that.categoryId) &&
                Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(houseId, listId, categoryId, productId);
    }
}
