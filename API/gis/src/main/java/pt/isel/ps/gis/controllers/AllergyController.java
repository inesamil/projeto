package pt.isel.ps.gis.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.ps.gis.bll.HouseAllergyService;
import pt.isel.ps.gis.bll.StockItemAllergenService;
import pt.isel.ps.gis.exceptions.BadRequestException;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.exceptions.NotFoundException;
import pt.isel.ps.gis.model.HouseAllergy;
import pt.isel.ps.gis.model.StockItem;
import pt.isel.ps.gis.model.inputModel.AllergiesInputModel;
import pt.isel.ps.gis.model.inputModel.AllergyInputModel;
import pt.isel.ps.gis.model.outputModel.HouseAllergiesOutputModel;
import pt.isel.ps.gis.model.outputModel.StockItemsAllergenOutputModel;

import java.util.List;

import static pt.isel.ps.gis.utils.HeadersUtils.setSirenContentType;

@RestController
@RequestMapping("/v1/houses/{house-id}/allergies")
public class AllergyController {

    private static final String BODY_ERROR_MSG = "You must specify the body correctly.";

    private final HouseAllergyService houseAllergyService;
    private final StockItemAllergenService stockItemAllergenService;

    public AllergyController(HouseAllergyService houseAllergyService, StockItemAllergenService stockItemAllergenService) {
        this.houseAllergyService = houseAllergyService;
        this.stockItemAllergenService = stockItemAllergenService;
    }

    @GetMapping("")
    public ResponseEntity<HouseAllergiesOutputModel> getHouseAllergies(
            @PathVariable("house-id") long houseId
    ) throws BadRequestException, NotFoundException {
        List<HouseAllergy> allergies;
        try {
            allergies = houseAllergyService.getAllergiesByHouseId(houseId);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new HouseAllergiesOutputModel(houseId, allergies), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @GetMapping("/{allergen}/items")
    public ResponseEntity<StockItemsAllergenOutputModel> getStockItemsAllergen(
            @PathVariable("house-id") long houseId,
            @PathVariable("allergen") String allergen
    ) throws BadRequestException, NotFoundException {
        List<StockItem> stockItemsAllergen;
        try {
            stockItemsAllergen = stockItemAllergenService.getStockItemsByHouseIdAndAllergenId(houseId, allergen);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new StockItemsAllergenOutputModel(houseId, allergen, stockItemsAllergen),
                setSirenContentType(headers), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<HouseAllergiesOutputModel> putAllergens(
            @PathVariable("house-id") long houseId,
            @RequestBody AllergiesInputModel body
    ) throws BadRequestException, NotFoundException {
        //TODO: fazer este controller e o serviço
        if (body.getAllergies() == null)
            throw new BadRequestException(BODY_ERROR_MSG);
        List<HouseAllergy> allergies;
        try {
            AllergiesInputModel.Allergy[] bodyAllergies = body.getAllergies();
            HouseAllergy[] houseAllergies = new HouseAllergy[bodyAllergies.length];
            for (int i = 0; i < houseAllergies.length; i++) {
                AllergiesInputModel.Allergy allergy = bodyAllergies[i];
                houseAllergies[i] = new HouseAllergy(houseId, allergy.getAllergy(), allergy.getAllergicsNum());
            }
            allergies = houseAllergyService.associateHouseAllergies(houseId, houseAllergies);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new HouseAllergiesOutputModel(houseId, allergies), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @PutMapping("/{allergen}")
    public ResponseEntity<HouseAllergiesOutputModel> putAllergen(
            @PathVariable("house-id") long houseId,
            @PathVariable("allergen") String allergen,
            @RequestBody AllergyInputModel body
    ) throws BadRequestException, NotFoundException {
        if (body.getAllergicsNum() == null)
            throw new BadRequestException(BODY_ERROR_MSG);
        List<HouseAllergy> allergies;
        try {
            houseAllergyService.associateHouseAllergy(houseId, allergen, body.getAllergicsNum());
            allergies = houseAllergyService.getAllergiesByHouseId(houseId);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new HouseAllergiesOutputModel(houseId, allergies), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @DeleteMapping("/{allergen}")
    public ResponseEntity<HouseAllergiesOutputModel> deleteAllergen(
            @PathVariable("house-id") long houseId,
            @PathVariable("allergen") String allergen
    ) throws BadRequestException, NotFoundException {
        List<HouseAllergy> allergies;
        try {
            houseAllergyService.deleteHouseAllergyByHouseAllergyId(houseId, allergen);
            allergies = houseAllergyService.getAllergiesByHouseId(houseId);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new HouseAllergiesOutputModel(houseId, allergies), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @DeleteMapping("")
    public ResponseEntity<HouseAllergiesOutputModel> deleteAllergies(
            @PathVariable("house-id") long houseId
    ) throws BadRequestException, NotFoundException {
        List<HouseAllergy> allergies;
        try {
            houseAllergyService.deleteAllHouseAllergiesByHouseId(houseId);
            allergies = houseAllergyService.getAllergiesByHouseId(houseId);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new HouseAllergiesOutputModel(houseId, allergies), setSirenContentType(headers),
                HttpStatus.OK);
    }
}
