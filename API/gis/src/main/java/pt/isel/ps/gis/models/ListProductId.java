package pt.isel.ps.gis.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ListProductId implements Serializable {

    @Id
    @Column(name = "house_id", nullable = false)
    private Long houseId;

    @Id
    @Column(name = "list_id", nullable = false)
    private Short listId;

    @Id
    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    @Id
    @Column(name = "product_id", nullable = false)
    private Integer productId;

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public Short getListId() {
        return listId;
    }

    public void setListId(Short listId) {
        this.listId = listId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
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
