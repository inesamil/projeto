package pt.isel.ps.gis.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.ps.gis.bll.HouseAllergyService;
import pt.isel.ps.gis.bll.HouseService;
import pt.isel.ps.gis.bll.StockItemAllergenService;
import pt.isel.ps.gis.exceptions.BadRequestException;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.exceptions.NotFoundException;
import pt.isel.ps.gis.model.HouseAllergy;
import pt.isel.ps.gis.model.StockItem;
import pt.isel.ps.gis.model.inputModel.AllergyInputModel;
import pt.isel.ps.gis.model.outputModel.HouseAllergiesOutputModel;
import pt.isel.ps.gis.model.outputModel.StockItemsAllergenOutputModel;

import java.util.List;

import static pt.isel.ps.gis.utils.HeadersUtils.setSirenContentType;

@RestController
@RequestMapping("/v1/houses/{house-id}/allergies")
public class AllergyController {

    private final HouseAllergyService houseAllergyService;
    private final HouseService houseService;
    private final StockItemAllergenService stockItemAllergenService;

    public AllergyController(HouseAllergyService houseAllergyService, HouseService houseService,
                             StockItemAllergenService stockItemAllergenService) {
        this.houseAllergyService = houseAllergyService;
        this.houseService = houseService;
        this.stockItemAllergenService = stockItemAllergenService;
    }

    @GetMapping("")
    public ResponseEntity<HouseAllergiesOutputModel> getHouseAllergies(
            @PathVariable("house-id") long houseId
    ) throws BadRequestException {
        checkHouse(houseId);
        List<HouseAllergy> allergies;
        try {
            allergies = houseAllergyService.getAllergiesByHouseId(houseId);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new HouseAllergiesOutputModel(houseId, allergies), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @GetMapping("/{allergen}/items")
    public ResponseEntity<StockItemsAllergenOutputModel> getStockItemsAllergen(
            @PathVariable("house-id") long houseId,
            @PathVariable("allergen") String allergen
    ) throws BadRequestException {
        checkAllergen(houseId, allergen);
        List<StockItem> stockItemsAllergen;
        try {
            stockItemsAllergen = stockItemAllergenService.getStockItemsByHouseIdAndAllergenId(houseId, allergen);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new StockItemsAllergenOutputModel(houseId, allergen, stockItemsAllergen),
                setSirenContentType(headers), HttpStatus.OK);
    }

    @PutMapping("/{allergen}")
    public ResponseEntity<HouseAllergiesOutputModel> putAllergen(
            @PathVariable("house-id") long houseId,
            @PathVariable("allergen") String allergen,
            @RequestBody AllergyInputModel body
    ) throws BadRequestException, NotFoundException {
        checkHouse(houseId);
        if (body.getAllergicsNum() == null)
            throw new BadRequestException("You must specify the body correctly.");
        List<HouseAllergy> allergies;
        try {
            HouseAllergy houseAllergy = new HouseAllergy(houseId, allergen, body.getAllergicsNum());
            if (isToUpdateAllergen(houseId, allergen))
                houseAllergyService.updateHouseAllergy(houseAllergy);
            else
                houseAllergyService.addHouseAllergy(houseAllergy);
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
        checkHouse(houseId);
        checkAllergen(houseId, allergen);
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

    private void checkHouse(long houseId) throws BadRequestException {
        try {
            if (!houseService.existsHouseByHouseId(houseId))
                throw new BadRequestException("House does not exist.");
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    private void checkAllergen(long houseId, String allergen) throws BadRequestException {
        try {
            if (!houseAllergyService.existsHouseAllergyByHouseAllergyId(houseId, allergen))
                throw new BadRequestException("Allergen does not exist.");
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    private boolean isToUpdateAllergen(long houseId, String allergen) throws BadRequestException {
        try {
            return houseAllergyService.existsHouseAllergyByHouseAllergyId(houseId, allergen);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
