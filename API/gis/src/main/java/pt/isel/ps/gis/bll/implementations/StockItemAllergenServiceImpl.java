package pt.isel.ps.gis.bll.implementations;

import org.springframework.stereotype.Service;
import pt.isel.ps.gis.bll.HouseAllergyService;
import pt.isel.ps.gis.bll.StockItemAllergenService;
import pt.isel.ps.gis.dal.repositories.AllergyRepository;
import pt.isel.ps.gis.dal.repositories.StockItemAllergyRepository;
import pt.isel.ps.gis.dal.repositories.StockItemRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.model.Allergy;
import pt.isel.ps.gis.model.StockItem;
import pt.isel.ps.gis.model.StockItemAllergyId;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.util.List;

@Service
public class StockItemAllergenServiceImpl implements StockItemAllergenService {

    private static final String ALLERGEN_NOT_EXIST = "Allergen does not exist.";

    private final StockItemAllergyRepository stockItemAllergyRepository;
    private final AllergyRepository allergyRepository;
    private final StockItemRepository stockItemRepository;
    private final HouseAllergyService houseAllergyService;

    public StockItemAllergenServiceImpl(StockItemAllergyRepository stockItemAllergyRepository, AllergyRepository allergyRepository, StockItemRepository stockItemRepository, HouseAllergyService houseAllergyService) {
        this.stockItemAllergyRepository = stockItemAllergyRepository;
        this.allergyRepository = allergyRepository;
        this.stockItemRepository = stockItemRepository;
        this.houseAllergyService = houseAllergyService;
    }

    @Override
    public boolean existsAllergenByStockItemAllergenId(long houseId, String stockItemSku, String allergen) throws EntityException {
        return stockItemAllergyRepository.existsById(new StockItemAllergyId(houseId, stockItemSku, allergen));
    }

    @Override
    public List<Allergy> getAllergensByStockItemId(long houseId, String stockItemSku) throws EntityException {
        ValidationsUtils.validateHouseId(houseId);
        ValidationsUtils.validateStockItemSku(stockItemSku);
        return allergyRepository.findAllByHouseIdAndStockitemSku(houseId, stockItemSku);
    }

    @Override
    public List<StockItem> getStockItemsByHouseIdAndAllergenId(long houseId, String allergen) throws EntityException {
        ValidationsUtils.validateHouseId(houseId);
        ValidationsUtils.validateAllergyAllergen(allergen);
        checkAllergen(houseId, allergen);
        return stockItemRepository.findAllByHouseIdAndAllergyAllergen(houseId, allergen);
    }

    private void checkAllergen(long houseId, String allergen) throws EntityException {
        if (!houseAllergyService.existsHouseAllergyByHouseAllergyId(houseId, allergen))
            throw new EntityException(ALLERGEN_NOT_EXIST);
    }
}
