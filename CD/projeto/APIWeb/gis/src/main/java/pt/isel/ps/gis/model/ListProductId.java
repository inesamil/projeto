package pt.isel.ps.gis.model;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
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

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    /**
     * CONSTRUTORES
     */
    protected ListProductId() {
    }

    public ListProductId(Long houseId, Short listId, Integer productId) throws EntityException {
        setHouseId(houseId);
        setListId(listId);
        setProductId(productId);
    }

    /**
     * GETTERS E SETTERS
     */
    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) throws EntityException {
        ValidationsUtils.validateHouseId(houseId);
        this.houseId = houseId;
    }

    public Short getListId() {
        return listId;
    }

    public void setListId(Short listId) throws EntityException {
        ValidationsUtils.validateListId(listId);
        this.listId = listId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) throws EntityException {
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
                Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(houseId, listId, productId);
    }
}
