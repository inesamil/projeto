package pt.isel.ps.gis.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.isel.ps.gis.bll.HouseAllergyService;
import pt.isel.ps.gis.bll.StockItemAllergenService;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.model.HouseAllergy;
import pt.isel.ps.gis.model.StockItem;
import pt.isel.ps.gis.model.outputModel.HouseAllergiesOutputModel;
import pt.isel.ps.gis.model.outputModel.StockItemsAllergenOutputModel;

import java.util.List;

import static pt.isel.ps.gis.utils.HeadersUtils.setCollectionContentType;

@RestController
@RequestMapping("/v1/houses/{house-id}/allergies")
public class AllergyController {

    private final HouseAllergyService houseAllergyService;
    private final StockItemAllergenService stockItemAllergenService;

    public AllergyController(HouseAllergyService houseAllergyService, StockItemAllergenService stockItemAllergenService) {
        this.houseAllergyService = houseAllergyService;
        this.stockItemAllergenService = stockItemAllergenService;
    }

    @GetMapping("")
    public ResponseEntity<HouseAllergiesOutputModel> getHouseAllergies(@PathVariable("house-id") long houseId) throws EntityException {
        List<HouseAllergy> allergies = houseAllergyService.getAllergiesByHouseId(houseId);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new HouseAllergiesOutputModel(houseId, allergies), setCollectionContentType(headers),
                HttpStatus.OK);
    }

    @GetMapping("/{allergen}/items")
    public ResponseEntity<StockItemsAllergenOutputModel> getStockItemsAllergen(
            @PathVariable("house-id") long houseId,
            @PathVariable("allergen") String allergen) throws EntityException {
        List<StockItem> stockItemsAllergen = stockItemAllergenService.getStockItemsByHouseIdAndAllergenId(houseId, allergen);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new StockItemsAllergenOutputModel(houseId, allergen, stockItemsAllergen),
                setCollectionContentType(headers), HttpStatus.OK);
    }
}
