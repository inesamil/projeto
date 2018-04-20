package pt.isel.ps.gis.model;

import pt.isel.ps.gis.exceptions.EntityException;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "stockitemmovement")
public class StockItemMovement {

    /**
     * COLUNAS
     */
    @EmbeddedId
    private StockItemMovementId id;

    /**
     * ASSOCIAÇÕES
     */
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "house_id", referencedColumnName = "house_id", nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "stockitem_sku", referencedColumnName = "stockitem_sku", nullable = false, insertable = false, updatable = false)
    })
    private StockItem stockitem;

    @ManyToOne
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

    public StockItemMovement(Long houseId, String stockitemSku, Short storageId, Boolean stockitemmovementType,
                             String stockitemmovementDatetime, Short stockitemmovementQuantity) throws EntityException {
        setId(houseId, stockitemSku, storageId, stockitemmovementType, stockitemmovementDatetime, stockitemmovementQuantity);
    }

    public StockItemMovement(Long houseId, String stockitemSku, Short storageId, Boolean stockitemmovementType,
                             Timestamp stockitemmovementDatetime, Short stockitemmovementQuantity) throws EntityException {
        setId(houseId, stockitemSku, storageId, stockitemmovementType, stockitemmovementDatetime, stockitemmovementQuantity);
    }

    /**
     * GETTERS E SETTERS
     */
    public StockItemMovementId getId() {
        return id;
    }

    private void setId(StockItemMovementId id) {
        this.id = id;
    }

    public void setId(Long houseId, String stockitemSku, Short storageId, Boolean stockitemmovementType, String stockitemmovementDatetime, Short stockitemmovementQuantity) throws EntityException {
        setId(new StockItemMovementId(houseId, stockitemSku, storageId, stockitemmovementType, stockitemmovementDatetime, stockitemmovementQuantity));
    }

    public void setId(Long houseId, String stockitemSku, Short storageId, Boolean stockitemmovementType, Timestamp stockitemmovementDatetime, Short stockitemmovementQuantity) throws EntityException {
        setId(new StockItemMovementId(houseId, stockitemSku, storageId, stockitemmovementType, stockitemmovementDatetime, stockitemmovementQuantity));
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
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
