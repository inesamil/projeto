package pt.isel.ps.gis.model.outputModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import pt.isel.ps.gis.model.StockItemMovement;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovementJsonObject {
    @JsonProperty(value = "house-id")
    private Long houseId;

    @JsonProperty(value = "stock-item-sku")
    private String stockItemSku;

    @JsonProperty(value = "storage-id")
    private Short storageId;

    @JsonProperty(value = "movement-type")
    private Boolean stockItemMovementType;

    @JsonProperty(value = "movement-datetime")
    private String stockItemMovementDatetime;

    @JsonProperty(value = "movement-quantity")
    private Short stockItemMovementQuantity;

    // Ctor
    public MovementJsonObject(StockItemMovement movement) {
        this.houseId = movement.getId().getHouseId();
        this.stockItemSku = movement.getId().getStockitemSku();
        this.storageId = movement.getId().getStorageId();
        this.stockItemMovementType = movement.getId().getStockitemmovementType();
        this.stockItemMovementDatetime = movement.getId().getStockitemmovementDatetime();
        this.stockItemMovementQuantity = movement.getId().getStockitemmovementQuantity();
    }
}
