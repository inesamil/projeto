package pt.isel.ps.gis.controllers;

import com.opencsv.bean.CsvToBeanBuilder;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.ps.gis.bll.StockItemMovementService;
import pt.isel.ps.gis.components.AuthenticationFacade;
import pt.isel.ps.gis.exceptions.*;
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
    private final MessageSource messageSource;
    private final AuthenticationFacade authenticationFacade;

    public StockItemMovementController(StockItemMovementService stockItemMovementService, MessageSource messageSource, AuthenticationFacade authenticationFacade) {
        this.stockItemMovementService = stockItemMovementService;
        this.messageSource = messageSource;
        this.authenticationFacade = authenticationFacade;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping("")
    public ResponseEntity<MovementsOutputModel> getMovements(
            @PathVariable("house-id") long houseId,
            StockItemMovementRequestParam params,
            Locale locale
    ) throws BadRequestException, NotFoundException, ForbiddenException {
        List<StockItemMovement> movements;
        String username = authenticationFacade.getAuthentication().getName();
        try {
            if (params.isNull())
                movements = stockItemMovementService.getStockItemMovementsByHouseId(username, houseId, locale);
            else {
                StockItemMovementService.MovementFilters filters = new StockItemMovementService.MovementFilters(
                        params.getType(),
                        params.getDatetime(),
                        params.getStorage(),
                        params.getItem()
                );
                movements = stockItemMovementService.getStockItemMovementsByHouseIdFiltered(username, houseId, filters, locale);
            }
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getUserFriendlyMessage());
        } catch (InsufficientPrivilegesException e) {
            throw new ForbiddenException(e.getMessage(), e.getUserFriendlyMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new MovementsOutputModel(houseId, movements), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping("")
    public ResponseEntity postMovement(
            @PathVariable("house-id") long houseId,
            @RequestBody MovementInputModel body,
            Locale locale
    ) throws BadRequestException, NotFoundException, ForbiddenException {
        CsvToBeanBuilder<TagCsv> builder = new CsvToBeanBuilder<>(new StringReader(body.getTag()));
        List<TagCsv> tagCsvList = builder.withType(TagCsv.class).build().parse();
        if (tagCsvList.size() <= 0)
            throw new BadRequestException("No empty Tags allowed.", messageSource.getMessage("no_empty_tags_allowed", null, locale));
        TagCsv tag = tagCsvList.get(0);
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
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getUserFriendlyMessage());
        } catch (InsufficientPrivilegesException e) {
            throw new ForbiddenException(e.getMessage(), e.getUserFriendlyMessage());
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
