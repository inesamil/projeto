package pt.isel.ps.gis.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.ps.gis.bll.StockItemAllergenService;
import pt.isel.ps.gis.bll.StockItemService;
import pt.isel.ps.gis.exceptions.BadRequestException;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.exceptions.NotFoundException;
import pt.isel.ps.gis.model.Allergy;
import pt.isel.ps.gis.model.StockItem;
import pt.isel.ps.gis.model.inputModel.StockItemInputModel;
import pt.isel.ps.gis.model.outputModel.AllergiesStockItemOutputModel;
import pt.isel.ps.gis.model.outputModel.StockItemOutputModel;
import pt.isel.ps.gis.model.outputModel.StockItemsOutputModel;
import pt.isel.ps.gis.model.requestParams.StockItemRequestParam;

import java.util.List;

import static pt.isel.ps.gis.utils.HeadersUtils.setSirenContentType;

@RestController
@RequestMapping("/v1/houses/{house-id}/items")
public class StockItemController {

    private final StockItemService stockItemService;
    private final StockItemAllergenService stockItemAllergenService;

    public StockItemController(StockItemService stockItemService, StockItemAllergenService stockItemAllergenService) {
        this.stockItemService = stockItemService;
        this.stockItemAllergenService = stockItemAllergenService;
    }

    @GetMapping("")
    public ResponseEntity<StockItemsOutputModel> getStockItems(
            @PathVariable("house-id") long houseId,
            StockItemRequestParam params
    ) throws BadRequestException, NotFoundException {
        List<StockItem> stockItems;
        try {
            if (params.isNull())
                stockItems = stockItemService.getStockItemsByHouseId(houseId);
            else {
                StockItemService.StockItemFilters filters = new StockItemService.StockItemFilters(
                        params.getProduct(),
                        params.getBrand(),
                        params.getVariety(),
                        params.getSegment(),
                        params.getStorage()
                );
                stockItems = stockItemService.getStockItemsByHouseIdFiltered(houseId, filters);
            }
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new StockItemsOutputModel(houseId, stockItems), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @GetMapping("/{stock-item-id}")
    public ResponseEntity<StockItemOutputModel> getStockItem(
            @PathVariable("house-id") long houseId,
            @PathVariable("stock-item-id") String sku
    ) throws NotFoundException, BadRequestException {
        StockItem stockItem;
        try {
            stockItem = stockItemService.getStockItemByStockItemId(houseId, sku);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new StockItemOutputModel(stockItem), setSirenContentType(headers), HttpStatus.OK);
    }

    @GetMapping("/{stock-item-id}/allergies")
    public ResponseEntity<AllergiesStockItemOutputModel> getAllergiesFromStockItem(
            @PathVariable("house-id") long houseId,
            @PathVariable("stock-item-id") String sku
    ) throws BadRequestException, NotFoundException {
        List<Allergy> allergens;
        try {
            allergens = stockItemAllergenService.getAllergensByStockItemId(houseId, sku);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new AllergiesStockItemOutputModel(houseId, sku, allergens),
                setSirenContentType(headers), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<StockItemOutputModel> postItem(
            @PathVariable("house-id") long houseId,
            @RequestBody StockItemInputModel body
    ) throws BadRequestException, NotFoundException {
        StockItem stockItem;
        try {
            stockItem = stockItemService.addStockItem(
                    houseId,
                    body.getProductId(),
                    body.getBrand(),
                    body.getSegment(),
                    body.getVariety(),
                    body.getQuantity(),
                    body.getDescription(),
                    body.getConservationStorage()
            );
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new StockItemOutputModel(stockItem), setSirenContentType(headers),
                HttpStatus.CREATED);
    }
}
