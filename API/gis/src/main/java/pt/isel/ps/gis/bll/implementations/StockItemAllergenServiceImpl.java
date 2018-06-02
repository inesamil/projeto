package pt.isel.ps.gis.bll.implementations;

import org.springframework.stereotype.Service;
import pt.isel.ps.gis.bll.StockItemAllergenService;
import pt.isel.ps.gis.dal.repositories.AllergyRepository;
import pt.isel.ps.gis.dal.repositories.HouseAllergyRepository;
import pt.isel.ps.gis.dal.repositories.StockItemAllergyRepository;
import pt.isel.ps.gis.dal.repositories.StockItemRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.Allergy;
import pt.isel.ps.gis.model.HouseAllergyId;
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
    private final HouseAllergyRepository houseAllergyRepository;

    public StockItemAllergenServiceImpl(StockItemAllergyRepository stockItemAllergyRepository, AllergyRepository allergyRepository, StockItemRepository stockItemRepository, HouseAllergyRepository houseAllergyRepository) {
        this.stockItemAllergyRepository = stockItemAllergyRepository;
        this.allergyRepository = allergyRepository;
        this.stockItemRepository = stockItemRepository;
        this.houseAllergyRepository = houseAllergyRepository;
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
    public List<StockItem> getStockItemsByHouseIdAndAllergenId(long houseId, String allergen) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateHouseId(houseId);
        ValidationsUtils.validateAllergyAllergen(allergen);
        checkAllergen(houseId, allergen);
        return stockItemRepository.findAllByHouseIdAndAllergyAllergen(houseId, allergen);
    }

    private void checkAllergen(long houseId, String allergen) throws EntityException, EntityNotFoundException {
        if (!houseAllergyRepository.existsById(new HouseAllergyId(houseId, allergen)))
            throw new EntityNotFoundException(ALLERGEN_NOT_EXIST);
    }
}
