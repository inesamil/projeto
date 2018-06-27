package pt.isel.ps.gis.controllers;

import com.opencsv.bean.CsvToBeanBuilder;
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
import pt.isel.ps.gis.model.TagCsv;
import pt.isel.ps.gis.model.inputModel.MovementInputModel;
import pt.isel.ps.gis.model.outputModel.MovementsOutputModel;
import pt.isel.ps.gis.model.requestParams.StockItemMovementRequestParam;

import java.io.StringReader;
import java.util.List;
import java.util.Locale;

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
            StockItemMovementRequestParam params,
            Locale locale
    ) throws BadRequestException, NotFoundException {
        List<StockItemMovement> movements;
        try {
            if (params.isNull())
                movements = stockItemMovementService.getStockItemMovementsByHouseId(houseId, locale);
            else {
                StockItemMovementService.MovementFilters filters = new StockItemMovementService.MovementFilters(
                        params.getType(),
                        params.getDatetime(),
                        params.getStorage(),
                        params.getItem()
                );
                movements = stockItemMovementService.getStockItemMovementsByHouseIdFiltered(houseId, filters, locale);
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
            @PathVariable("house-id") long houseId,
            @RequestBody MovementInputModel body,
            Locale locale
    ) throws BadRequestException, NotFoundException {
        // TODO o que acontece qnd um atributo é null? qual é a excecao lancada pelo jackson e no qe resulta essa excecao?
        CsvToBeanBuilder<TagCsv> builder = new CsvToBeanBuilder<>(new StringReader(body.getTag()));
        List<TagCsv> tagCsvList = builder.withType(TagCsv.class).build().parse();
        if (tagCsvList.size() <= 0) throw new BadRequestException(""); // TODO ver a mensagem da excecao
        TagCsv tag = tagCsvList.get(0);
        List<StockItemMovement> movements;
        try {
            stockItemMovementService.addStockItemMovement(
                    houseId,
                    body.getStorageId(),
                    body.getType(),
                    body.getQuantity(),
                    tag.getProductName(),
                    tag.getBrand(),
                    tag.getVariety(),
                    tag.getSegment(),
                    tag.getConditions(),
                    tag.getDescription(),
                    tag.getDate(),
                    locale
            );
            movements = stockItemMovementService.getStockItemMovementsByHouseId(houseId, locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new MovementsOutputModel(houseId, movements), setSirenContentType(headers),
                HttpStatus.CREATED);
    }
}
