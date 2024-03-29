package pt.isel.ps.gis.controllers;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.ps.gis.bll.HouseAllergyService;
import pt.isel.ps.gis.bll.StockItemAllergenService;
import pt.isel.ps.gis.components.AuthenticationFacade;
import pt.isel.ps.gis.exceptions.*;
import pt.isel.ps.gis.model.HouseAllergy;
import pt.isel.ps.gis.model.StockItem;
import pt.isel.ps.gis.model.inputModel.AllergiesInputModel;
import pt.isel.ps.gis.model.inputModel.AllergyInputModel;
import pt.isel.ps.gis.model.outputModel.HouseAllergiesOutputModel;
import pt.isel.ps.gis.model.outputModel.StockItemsAllergenOutputModel;

import java.util.List;
import java.util.Locale;

import static pt.isel.ps.gis.utils.HeadersUtils.setSirenContentType;

@RestController
@RequestMapping("/v1/houses/{house-id}/allergies")
public class AllergyController {

    private final HouseAllergyService houseAllergyService;
    private final StockItemAllergenService stockItemAllergenService;
    private final AuthenticationFacade authenticationFacade;
    private final MessageSource messageSource;

    public AllergyController(HouseAllergyService houseAllergyService, StockItemAllergenService stockItemAllergenService, MessageSource messageSource, AuthenticationFacade authenticationFacade) {
        this.houseAllergyService = houseAllergyService;
        this.stockItemAllergenService = stockItemAllergenService;
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
    public ResponseEntity<HouseAllergiesOutputModel> getHouseAllergies(
            @PathVariable("house-id") long houseId,
            Locale locale
    ) throws BadRequestException, NotFoundException, ForbiddenException {
        List<HouseAllergy> allergies;
        String username = authenticationFacade.getAuthentication().getName();
        try {
            allergies = houseAllergyService.getAllergiesByHouseId(username, houseId, locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getUserFriendlyMessage());
        } catch (InsufficientPrivilegesException e) {
            throw new ForbiddenException(e.getMessage(), e.getUserFriendlyMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new HouseAllergiesOutputModel(houseId, allergies), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping("/{allergen}/items")
    public ResponseEntity<StockItemsAllergenOutputModel> getStockItemsAllergen(
            @PathVariable("house-id") long houseId,
            @PathVariable("allergen") String allergen,
            Locale locale
    ) throws BadRequestException, NotFoundException, ForbiddenException {
        List<StockItem> stockItemsAllergen;
        String username = authenticationFacade.getAuthentication().getName();
        try {
            stockItemsAllergen = stockItemAllergenService.getStockItemsByHouseIdAndAllergenId(username, houseId, allergen, locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getUserFriendlyMessage());
        } catch (InsufficientPrivilegesException e) {
            throw new ForbiddenException(e.getMessage(), e.getUserFriendlyMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new StockItemsAllergenOutputModel(houseId, allergen, stockItemsAllergen),
                setSirenContentType(headers), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PutMapping("")
    public ResponseEntity<HouseAllergiesOutputModel> putAllergens(
            @PathVariable("house-id") long houseId,
            @RequestBody AllergiesInputModel body,
            Locale locale
    ) throws BadRequestException, NotFoundException, ForbiddenException {
        if (body.getAllergies() == null)
            throw new BadRequestException(messageSource.getMessage("body_Error_Msg", null, locale), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        List<HouseAllergy> allergies;
        String username = authenticationFacade.getAuthentication().getName();
        try {
            AllergiesInputModel.Allergy[] bodyAllergies = body.getAllergies();
            HouseAllergy[] houseAllergies = new HouseAllergy[bodyAllergies.length];
            for (int i = 0; i < houseAllergies.length; i++) {
                AllergiesInputModel.Allergy allergy = bodyAllergies[i];
                houseAllergies[i] = new HouseAllergy(houseId, allergy.getAllergy(), allergy.getAllergicsNum());
            }
            allergies = houseAllergyService.associateHouseAllergies(username, houseId, houseAllergies, locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), e.getUserFriendlyMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getUserFriendlyMessage());
        } catch (InsufficientPrivilegesException e) {
            throw new ForbiddenException(e.getMessage(), e.getUserFriendlyMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new HouseAllergiesOutputModel(houseId, allergies), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PutMapping("/{allergen}")
    public ResponseEntity<HouseAllergiesOutputModel> putAllergen(
            @PathVariable("house-id") long houseId,
            @PathVariable("allergen") String allergen,
            @RequestBody AllergyInputModel body,
            Locale locale
    ) throws BadRequestException, NotFoundException, ForbiddenException {
        List<HouseAllergy> allergies;
        String username = authenticationFacade.getAuthentication().getName();
        try {
            houseAllergyService.associateHouseAllergy(username, houseId, allergen, body.getAllergicsNum(), locale);
            allergies = houseAllergyService.getAllergiesByHouseId(username, houseId, locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), e.getUserFriendlyMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getUserFriendlyMessage());
        } catch (InsufficientPrivilegesException e) {
            throw new ForbiddenException(e.getMessage(), e.getUserFriendlyMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new HouseAllergiesOutputModel(houseId, allergies), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @DeleteMapping("")
    public ResponseEntity<HouseAllergiesOutputModel> deleteAllergies(
            @PathVariable("house-id") long houseId,
            Locale locale
    ) throws BadRequestException, NotFoundException, ForbiddenException {
        List<HouseAllergy> allergies;
        String username = authenticationFacade.getAuthentication().getName();
        try {
            houseAllergyService.deleteAllHouseAllergiesByHouseId(username, houseId, locale);
            allergies = houseAllergyService.getAllergiesByHouseId(username, houseId, locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getUserFriendlyMessage());
        } catch (InsufficientPrivilegesException e) {
            throw new ForbiddenException(e.getMessage(), e.getUserFriendlyMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new HouseAllergiesOutputModel(houseId, allergies), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @DeleteMapping("/{allergen}")
    public ResponseEntity<HouseAllergiesOutputModel> deleteAllergen(
            @PathVariable("house-id") long houseId,
            @PathVariable("allergen") String allergen,
            Locale locale
    ) throws BadRequestException, NotFoundException, ForbiddenException {
        List<HouseAllergy> allergies;
        String username = authenticationFacade.getAuthentication().getName();
        try {
            houseAllergyService.deleteHouseAllergyByHouseAllergyId(username, houseId, allergen, locale);
            allergies = houseAllergyService.getAllergiesByHouseId(username, houseId, locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), messageSource.getMessage("request_Not_Be_Completed", null, locale));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getUserFriendlyMessage());
        } catch (InsufficientPrivilegesException e) {
            throw new ForbiddenException(e.getMessage(), e.getUserFriendlyMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new HouseAllergiesOutputModel(houseId, allergies), setSirenContentType(headers),
                HttpStatus.OK);
    }
}
