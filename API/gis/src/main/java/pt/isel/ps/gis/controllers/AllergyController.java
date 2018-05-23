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
    ) throws EntityException, BadRequestException {
        checkHouse(houseId);
        List<HouseAllergy> allergies = houseAllergyService.getAllergiesByHouseId(houseId);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new HouseAllergiesOutputModel(houseId, allergies), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @GetMapping("/{allergen}/items")
    public ResponseEntity<StockItemsAllergenOutputModel> getStockItemsAllergen(
            @PathVariable("house-id") long houseId,
            @PathVariable("allergen") String allergen
    ) throws EntityException, BadRequestException {
        checkAllergen(houseId, allergen);
        List<StockItem> stockItemsAllergen = stockItemAllergenService.getStockItemsByHouseIdAndAllergenId(houseId, allergen);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new StockItemsAllergenOutputModel(houseId, allergen, stockItemsAllergen),
                setSirenContentType(headers), HttpStatus.OK);
    }

    @PutMapping("/{allergen}")
    public ResponseEntity<HouseAllergiesOutputModel> putAllergen(
            @PathVariable("house-id") long houseId,
            @PathVariable("allergen") String allergen,
            @RequestBody AllergyInputModel body
    ) throws BadRequestException, EntityException, EntityNotFoundException {
        checkHouse(houseId);
        if (body.getAllergicsNum() == null)
            throw new BadRequestException("You must specify the body correctly.");
        HouseAllergy houseAllergy = new HouseAllergy(houseId, allergen, body.getAllergicsNum());
        if (isToUpdateAllergen(houseId, allergen))
            houseAllergyService.updateHouseAllergy(houseAllergy);
        else
            houseAllergyService.addHouseAllergy(houseAllergy);
        List<HouseAllergy> allergies = houseAllergyService.getAllergiesByHouseId(houseId);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new HouseAllergiesOutputModel(houseId, allergies), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @DeleteMapping("/{allergen}")
    public ResponseEntity<HouseAllergiesOutputModel> deleteAllergen(
            @PathVariable("house-id") long houseId,
            @PathVariable("allergen") String allergen
    ) throws BadRequestException, EntityException, EntityNotFoundException {
        checkHouse(houseId);
        checkAllergen(houseId, allergen);
        houseAllergyService.deleteHouseAllergyByHouseAllergyId(houseId, allergen);
        List<HouseAllergy> allergies = houseAllergyService.getAllergiesByHouseId(houseId);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new HouseAllergiesOutputModel(houseId, allergies), setSirenContentType(headers),
                HttpStatus.OK);
    }

    private void checkHouse(long houseId) throws EntityException, BadRequestException {
        if (!houseService.existsHouseByHouseId(houseId))
            throw new BadRequestException("House does not exist.");
    }

    private void checkAllergen(long houseId, String allergen) throws EntityException, BadRequestException {
        if (!houseAllergyService.existsHouseAllergyByHouseAllergyId(houseId, allergen))
            throw new BadRequestException("Allergen does not exist.");
    }

    private boolean isToUpdateAllergen(long houseId, String allergen) throws EntityException {
        return houseAllergyService.existsHouseAllergyByHouseAllergyId(houseId, allergen);
    }
}
