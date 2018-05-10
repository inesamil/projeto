package pt.isel.ps.gis.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.ps.gis.bll.HouseService;
import pt.isel.ps.gis.bll.StockItemService;
import pt.isel.ps.gis.exceptions.BadRequestException;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.NotFoundException;
import pt.isel.ps.gis.model.StockItem;
import pt.isel.ps.gis.model.inputModel.StockItemInputModel;
import pt.isel.ps.gis.model.outputModel.AllergiesStockItemOutputModel;
import pt.isel.ps.gis.model.outputModel.StockItemOutputModel;
import pt.isel.ps.gis.model.outputModel.StockItemsOutputModel;
import pt.isel.ps.gis.model.requestParams.StockItemRequestParam;
import pt.isel.ps.gis.utils.InputUtils;

import java.util.List;
import java.util.Optional;

import static pt.isel.ps.gis.utils.HeadersUtils.setCollectionContentType;
import static pt.isel.ps.gis.utils.HeadersUtils.setSirenContentType;

@RestController
@RequestMapping("/v1/houses/{house-id}/items")
public class StockItemController {

    private final StockItemService stockItemService;
    private final HouseService houseService;

    public StockItemController(StockItemService stockItemService, HouseService houseService) {
        this.stockItemService = stockItemService;
        this.houseService = houseService;
    }

    @GetMapping("")
    public ResponseEntity<StockItemsOutputModel> getStockItems(
            @PathVariable("house-id") long houseId,
            StockItemRequestParam params
    ) throws EntityException, BadRequestException {
        checkHouse(houseId);
        List<StockItem> stockItems;
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
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new StockItemsOutputModel(houseId, stockItems), setCollectionContentType(headers),
                HttpStatus.OK);
    }

    @GetMapping("/{stock-item-id}")
    public ResponseEntity<StockItemOutputModel> getStockItem(
            @PathVariable("house-id") long houseId,
            @PathVariable("stock-item-id") String sku) throws EntityException, NotFoundException, BadRequestException {
        checkHouse(houseId);
        Optional<StockItem> stockItemOptional = stockItemService.getStockItemByStockItemId(houseId, sku);
        StockItem stockItem = stockItemOptional.orElseThrow(NotFoundException::new);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new StockItemOutputModel(stockItem), setSirenContentType(headers), HttpStatus.OK);
    }

    @GetMapping("/{stock-item-id}/allergies")
    public ResponseEntity<AllergiesStockItemOutputModel> getAllergiesFromStockItem(
            @PathVariable("house-id") long houseId,
            @PathVariable("stock-item-id") String sku) throws BadRequestException, EntityException {
        checkHouse(houseId);
        checkStockItem(houseId, sku);
        // TODO falta metodo no servico
        return null;
    }

    @PostMapping("")
    public ResponseEntity<StockItemsOutputModel> postItem(
            @PathVariable("house-id") long houseId,
            @RequestBody StockItemInputModel body
    ) throws EntityException, BadRequestException {
        checkHouse(houseId);
        String[] segmentSplitted = InputUtils.splitNumbersFromLetters(body.getSegment());
        if (segmentSplitted.length != 2)
            throw new BadRequestException("Segment is invalid.");
        stockItemService.addStockItem(new StockItem(
                houseId,
                body.getCategoryId(),
                body.getProductId(),
                body.getBrand(),
                Float.parseFloat(segmentSplitted[0]),
                body.getVariety(),
                body.getQuantity(),
                segmentSplitted[1],
                body.getDescription(),
                body.getConservationStorage()
        ));
        List<StockItem> items = stockItemService.getStockItemsByHouseId(houseId);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new StockItemsOutputModel(houseId, items), setCollectionContentType(headers),
                HttpStatus.CREATED);
    }

    private void checkHouse(long houseId) throws EntityException, BadRequestException {
        if (!houseService.existsHouseByHouseId(houseId))
            throw new BadRequestException("House does not exist.");
    }

    private void checkStockItem(long houseId, String sku) throws EntityException, BadRequestException {
        if (!stockItemService.existsStockItemByStockItemId(houseId, sku))
            throw new BadRequestException("Stock Item does not exist.");
    }
}
