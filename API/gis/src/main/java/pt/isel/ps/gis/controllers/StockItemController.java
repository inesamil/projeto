package pt.isel.ps.gis.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.isel.ps.gis.bll.StockItemService;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.NotFoundException;
import pt.isel.ps.gis.model.StockItem;
import pt.isel.ps.gis.model.outputModel.AllergiesStockItemOutputModel;
import pt.isel.ps.gis.model.outputModel.StockItemOutputModel;
import pt.isel.ps.gis.model.outputModel.StockItemsOutputModel;

import java.util.List;
import java.util.Optional;

import static pt.isel.ps.gis.utils.HeadersUtils.setCollectionContentType;
import static pt.isel.ps.gis.utils.HeadersUtils.setSirenContentType;

@RestController
@RequestMapping("v1/houses/{house-id}/items")
public class StockItemController {

    private final StockItemService stockItemService;

    public StockItemController(StockItemService stockItemService) {
        this.stockItemService = stockItemService;
    }

    @GetMapping("")
    public ResponseEntity<StockItemsOutputModel> getStockItems(@PathVariable("house-id") long houseId) throws EntityException {
        List<StockItem> stockItems = stockItemService.getStockItemsByHouseId(houseId);
        // TODO qual usar?
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new StockItemsOutputModel(houseId, stockItems), setCollectionContentType(headers),
                HttpStatus.OK);
    }

    @GetMapping("/{stock-item-id}")
    public ResponseEntity<StockItemOutputModel> getStockItem(
            @PathVariable("house-id") long houseId,
            @PathVariable("stock-item-id") String sku) throws EntityException, NotFoundException {
        Optional<StockItem> stockItemOptional = stockItemService.getStockItemByStockItemId(houseId, sku);
        StockItem stockItem = stockItemOptional.orElseThrow(NotFoundException::new);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new StockItemOutputModel(stockItem), setSirenContentType(headers), HttpStatus.OK);
    }

    @GetMapping("/{stock-item-id}/allergies")
    public ResponseEntity<AllergiesStockItemOutputModel> getAllergiesFromStockItem(
            @PathVariable("house-id") long houseId,
            @PathVariable("stock-item-id") String sku) {
        // TODO falta metodo no servico
        return null;
    }
}
