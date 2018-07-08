package pt.isel.ps.gis.model;

import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.utils.ValidationsUtils;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "stockitemmovement")
public class StockItemMovement {

    /**
     * COLUNAS
     */
    @EmbeddedId
    private StockItemMovementId id;

    @Basic
    @Column(name = "stockitemmovement_quantity", nullable = false)
    private Short stockitemmovementQuantity;

    /**
     * ASSOCIAÇÕES
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "house_id", referencedColumnName = "house_id", nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "stockitem_sku", referencedColumnName = "stockitem_sku", nullable = false, insertable = false, updatable = false)
    })
    private StockItem stockitem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "house_id", referencedColumnName = "house_id", nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "storage_id", referencedColumnName = "storage_id", nullable = false, insertable = false, updatable = false)
    })
    private Storage storage;

    /**
     * CONSTRUTORES
     */
    protected StockItemMovement() {
    }

    public StockItemMovement(
            StockItemMovementId id, Short stockitemmovementQuantity
    ) throws EntityException {
        this.id = id;
        setStockitemmovementQuantity(stockitemmovementQuantity);
    }

    public StockItemMovement(
            Long houseId, String stockitemSku, Short storageId, Boolean stockitemmovementType,
            String stockitemmovementDatetime, Short stockitemmovementQuantity
    ) throws EntityException {
        setId(houseId, stockitemSku, storageId, stockitemmovementType, stockitemmovementDatetime);
        setStockitemmovementQuantity(stockitemmovementQuantity);
    }

    /**
     * GETTERS E SETTERS
     */
    public StockItemMovementId getId() {
        return id;
    }

    public void setId(StockItemMovementId id) {
        this.id = id;
    }

    public void setId(Long houseId, String stockitemSku, Short storageId, Boolean stockitemmovementType, String stockitemmovementDatetime) throws EntityException {
        setId(new StockItemMovementId(houseId, stockitemSku, storageId, stockitemmovementType, stockitemmovementDatetime));
    }

    public Short getStockitemmovementQuantity() {
        return stockitemmovementQuantity;
    }

    public void setStockitemmovementQuantity(Short stockitemmovementQuantity) throws EntityException {
        ValidationsUtils.validateStockItemMovementQuantity(stockitemmovementQuantity);
        this.stockitemmovementQuantity = stockitemmovementQuantity;
    }

    public StockItem getStockitem() {
        return stockitem;
    }

    public void setStockitem(StockItem stockitem) {
        this.stockitem = stockitem;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        StockItemMovement that = (StockItemMovement) obj;
        return Objects.equals(id, that.id) &&
                Objects.equals(stockitemmovementQuantity, that.stockitemmovementQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stockitemmovementQuantity);
    }
}
