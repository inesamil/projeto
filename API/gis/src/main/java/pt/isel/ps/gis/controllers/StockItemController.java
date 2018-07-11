package pt.isel.ps.gis.controllers;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.isel.ps.gis.bll.StockItemAllergenService;
import pt.isel.ps.gis.bll.StockItemService;
import pt.isel.ps.gis.components.AuthenticationFacade;
import pt.isel.ps.gis.exceptions.*;
import pt.isel.ps.gis.model.Allergy;
import pt.isel.ps.gis.model.StockItem;
import pt.isel.ps.gis.model.outputModel.AllergiesStockItemOutputModel;
import pt.isel.ps.gis.model.outputModel.StockItemOutputModel;
import pt.isel.ps.gis.model.outputModel.StockItemsOutputModel;
import pt.isel.ps.gis.model.requestParams.StockItemRequestParam;
import pt.isel.ps.gis.utils.AuthorizationProvider;

import java.util.List;
import java.util.Locale;

import static pt.isel.ps.gis.utils.HeadersUtils.setSirenContentType;

@RestController
@RequestMapping("/v1/houses/{house-id}/items")
public class StockItemController {

    private final StockItemService stockItemService;
    private final StockItemAllergenService stockItemAllergenService;
    private final MessageSource messageSource;
    private final AuthenticationFacade authenticationFacade;

    public StockItemController(StockItemService stockItemService, StockItemAllergenService stockItemAllergenService, MessageSource messageSource, AuthorizationProvider authorizationProvider, AuthenticationFacade authenticationFacade) {
        this.stockItemService = stockItemService;
        this.stockItemAllergenService = stockItemAllergenService;
        this.messageSource = messageSource;
        this.authenticationFacade = authenticationFacade;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping("")
    public ResponseEntity<StockItemsOutputModel> getStockItems(
            @PathVariable("house-id") long houseId,
            StockItemRequestParam params,
            Locale locale
    ) throws BadRequestException, NotFoundException, ForbiddenException {
        List<StockItem> stockItems;
        String username = authenticationFacade.getAuthentication().getName();
        try {
            if (params.isNull())
                stockItems = stockItemService.getStockItemsByHouseId(username, houseId, locale);
            else {
                StockItemService.StockItemFilters filters = new StockItemService.StockItemFilters(
                        params.getProduct(),
                        params.getBrand(),
                        params.getVariety(),
                        params.getSegment(),
                        params.getStorage()
                );
                stockItems = stockItemService.getStockItemsByHouseIdFiltered(username, houseId, filters, locale);
            }
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getUserFriendlyMessage());
        } catch (InsufficientPrivilegesException e) {
            throw new ForbiddenException(e.getMessage(), e.getUserFriendlyMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new StockItemsOutputModel(houseId, stockItems), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping("/{stock-item-id}")
    public ResponseEntity<StockItemOutputModel> getStockItem(
            @PathVariable("house-id") long houseId,
            @PathVariable("stock-item-id") String sku,
            Locale locale
    ) throws NotFoundException, BadRequestException, ForbiddenException {
        StockItem stockItem;
        String username = authenticationFacade.getAuthentication().getName();
        try {
            stockItem = stockItemService.getStockItemByStockItemId(username, houseId, sku, locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getUserFriendlyMessage());
        } catch (InsufficientPrivilegesException e) {
            throw new ForbiddenException(e.getMessage(), e.getUserFriendlyMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new StockItemOutputModel(stockItem), setSirenContentType(headers), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping("/{stock-item-id}/allergies")
    public ResponseEntity<AllergiesStockItemOutputModel> getAllergiesFromStockItem(
            @PathVariable("house-id") long houseId,
            @PathVariable("stock-item-id") String sku,
            Locale locale
    ) throws BadRequestException, NotFoundException, ForbiddenException {
        List<Allergy> allergens;
        String username = authenticationFacade.getAuthentication().getName();
        try {
            allergens = stockItemAllergenService.getAllergensByStockItemId(username, houseId, sku, locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getUserFriendlyMessage());
        } catch (InsufficientPrivilegesException e) {
            throw new ForbiddenException(e.getMessage(), e.getUserFriendlyMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new AllergiesStockItemOutputModel(houseId, sku, allergens),
                setSirenContentType(headers), HttpStatus.OK);
    }
}
