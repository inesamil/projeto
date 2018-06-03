package pt.isel.ps.gis.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.ps.gis.bll.StockItemMovementService;
import pt.isel.ps.gis.exceptions.BadRequestException;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.exceptions.NotFoundException;
import pt.isel.ps.gis.model.StockItemMovement;
import pt.isel.ps.gis.model.outputModel.MovementsOutputModel;
import pt.isel.ps.gis.model.requestParams.StockItemMovementRequestParam;

import java.util.List;

import static pt.isel.ps.gis.utils.HeadersUtils.setSirenContentType;

@RestController
@RequestMapping("/v1/houses/{house-id}/movements")
public class StockItemMovementController {

    private final StockItemMovementService stockItemMovementService;

    public StockItemMovementController(StockItemMovementService stockItemMovementService) {
        this.stockItemMovementService = stockItemMovementService;
    }

    @GetMapping("")
    public ResponseEntity<MovementsOutputModel> getMovements(
            @PathVariable("house-id") long houseId,
            StockItemMovementRequestParam params
    ) throws BadRequestException, NotFoundException {
        List<StockItemMovement> movements;
        try {
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
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new MovementsOutputModel(houseId, movements), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<MovementsOutputModel> postMovement(
            @PathVariable("house-id") long houseId
    ) throws BadRequestException, NotFoundException {
        // TODO falta definir o body
        List<StockItemMovement> movements;
        try {
            movements = stockItemMovementService.getStockItemMovementsByHouseId(houseId);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new MovementsOutputModel(houseId, movements), setSirenContentType(headers),
                HttpStatus.OK);
    }
}
