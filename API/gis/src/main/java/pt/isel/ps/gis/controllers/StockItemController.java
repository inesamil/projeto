package pt.isel.ps.gis.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.ps.gis.bll.HouseService;
import pt.isel.ps.gis.bll.StockItemAllergenService;
import pt.isel.ps.gis.bll.StockItemService;
import pt.isel.ps.gis.exceptions.BadRequestException;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.NotFoundException;
import pt.isel.ps.gis.model.Allergy;
import pt.isel.ps.gis.model.StockItem;
import pt.isel.ps.gis.model.inputModel.StockItemInputModel;
import pt.isel.ps.gis.model.outputModel.AllergiesStockItemOutputModel;
import pt.isel.ps.gis.model.outputModel.StockItemOutputModel;
import pt.isel.ps.gis.model.outputModel.StockItemsOutputModel;
import pt.isel.ps.gis.model.requestParams.StockItemRequestParam;
import pt.isel.ps.gis.utils.InputUtils;

import java.util.List;
import java.util.Optional;

import static pt.isel.ps.gis.utils.HeadersUtils.setSirenContentType;

@RestController
@RequestMapping("/v1/houses/{house-id}/items")
public class StockItemController {

    private static final String SEGMENT_INVALID = "Segment is invalid.";
    private static final String HOUSE_NOT_EXIST = "House does not exist.";
    private static final String STOCK_ITEM_NOT_EXIST = "Stock Item does not exist.";

    private final StockItemService stockItemService;
    private final HouseService houseService;
    private final StockItemAllergenService stockItemAllergenService;

    public StockItemController(StockItemService stockItemService, HouseService houseService,
                               StockItemAllergenService stockItemAllergenService) {
        this.stockItemService = stockItemService;
        this.houseService = houseService;
        this.stockItemAllergenService = stockItemAllergenService;
    }

    @GetMapping("")
    public ResponseEntity<StockItemsOutputModel> getStockItems(
            @PathVariable("house-id") long houseId,
            StockItemRequestParam params
    ) throws BadRequestException {
        checkHouse(houseId);
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
        Optional<StockItem> stockItemOptional;
        try {
            stockItemOptional = stockItemService.getStockItemByStockItemId(houseId, sku);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        }
        StockItem stockItem = stockItemOptional.orElseThrow(NotFoundException::new);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new StockItemOutputModel(stockItem), setSirenContentType(headers), HttpStatus.OK);
    }

    @GetMapping("/{stock-item-id}/allergies")
    public ResponseEntity<AllergiesStockItemOutputModel> getAllergiesFromStockItem(
            @PathVariable("house-id") long houseId,
            @PathVariable("stock-item-id") String sku
    ) throws BadRequestException {
        checkStockItem(houseId, sku);
        List<Allergy> allergens;
        try {
            allergens = stockItemAllergenService.getAllergensByStockItemId(houseId, sku);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new AllergiesStockItemOutputModel(houseId, sku, allergens),
                setSirenContentType(headers), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<StockItemsOutputModel> postItem(
            @PathVariable("house-id") long houseId,
            @RequestBody StockItemInputModel body
    ) throws BadRequestException {
        checkHouse(houseId);
        String[] segmentSplitted = InputUtils.splitNumbersFromLetters(body.getSegment());
        if (segmentSplitted.length != 2)
            throw new BadRequestException(SEGMENT_INVALID);
        List<StockItem> items;
        try {
            stockItemService.addStockItem(new StockItem(
                    houseId,
                    body.getProductId(),
                    body.getBrand(),
                    Float.parseFloat(segmentSplitted[0]),
                    body.getVariety(),
                    body.getQuantity(),
                    segmentSplitted[1],
                    body.getDescription(),
                    body.getConservationStorage()
            ));
            items = stockItemService.getStockItemsByHouseId(houseId);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new StockItemsOutputModel(houseId, items), setSirenContentType(headers),
                HttpStatus.CREATED);
    }

    private void checkHouse(long houseId) throws BadRequestException {
        try {
            if (!houseService.existsHouseByHouseId(houseId))
                throw new BadRequestException(HOUSE_NOT_EXIST);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    private void checkStockItem(long houseId, String sku) throws BadRequestException {
        try {
            if (!stockItemService.existsStockItemByStockItemId(houseId, sku))
                throw new BadRequestException(STOCK_ITEM_NOT_EXIST);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
