package pt.isel.ps.gis.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.isel.ps.gis.bll.HouseService;
import pt.isel.ps.gis.bll.StockItemMovementService;
import pt.isel.ps.gis.exceptions.BadRequestException;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.model.StockItemMovement;
import pt.isel.ps.gis.model.outputModel.MovementsOutputModel;
import pt.isel.ps.gis.model.requestParams.StockItemMovementRequestParam;

import java.util.List;

import static pt.isel.ps.gis.utils.HeadersUtils.setCollectionContentType;

@RestController
@RequestMapping("/v1/houses/{house-id}/movements")
public class StockItemMovementController {

    private final StockItemMovementService stockItemMovementService;
    private final HouseService houseService;

    public StockItemMovementController(StockItemMovementService stockItemMovementService, HouseService houseService) {
        this.stockItemMovementService = stockItemMovementService;
        this.houseService = houseService;
    }

    @GetMapping("")
    public ResponseEntity<MovementsOutputModel> getMovements(
            @PathVariable("house-id") long houseId,
            StockItemMovementRequestParam params
    ) throws EntityException, BadRequestException {
        checkHouse(houseId);
        List<StockItemMovement> movements;
        if (params.isNull())
            movements = stockItemMovementService.getStockItemMovementsByHouseId(houseId);
        else {
            StockItemMovementService.MovementFilters filters = new StockItemMovementService.MovementFilters(
                    params.getType(),
                    params.getDatetime(),
                    params.getStorage(),
                    params.getItem()
            );
            movements = stockItemMovementService.getStockItemMovementsByHouseIdFiltered(houseId, filters);
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new MovementsOutputModel(houseId, movements), setCollectionContentType(headers),
                HttpStatus.OK);
    }

    private void checkHouse(long houseId) throws EntityException, BadRequestException {
        if (!houseService.existsHouseByHouseId(houseId))
            throw new BadRequestException("House does not exist.");
    }
}
