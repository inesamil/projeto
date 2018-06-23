package pt.isel.ps.gis.bll.implementations;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.isel.ps.gis.bll.StockItemAllergenService;
import pt.isel.ps.gis.dal.repositories.AllergyRepository;
import pt.isel.ps.gis.dal.repositories.HouseAllergyRepository;
import pt.isel.ps.gis.dal.repositories.StockItemAllergyRepository;
import pt.isel.ps.gis.dal.repositories.StockItemRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.model.*;

import java.util.List;

@Service
public class StockItemAllergenServiceImpl implements StockItemAllergenService {

    private static final String ALLERGEN_NOT_EXIST = "Allergen does not exist.";
    private static final String STOCK_ITEM_NOT_EXIST = "Stock Item does not exist.";

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

    @Transactional
    @Override
    public List<Allergy> getAllergensByStockItemId(long houseId, String stockItemSku) throws EntityException, EntityNotFoundException {
        StockItemId stockItemId = new StockItemId(houseId, stockItemSku);
        checkStockItem(stockItemId);
        return allergyRepository.findAllByHouseIdAndStockitemSku(houseId, stockItemSku);
    }

    @Transactional
    @Override
    public List<StockItem> getStockItemsByHouseIdAndAllergenId(long houseId, String allergen) throws EntityException, EntityNotFoundException {
        HouseAllergyId houseAllergyId = new HouseAllergyId(houseId, allergen);
        checkAllergen(houseAllergyId);
        return stockItemRepository.findAllByHouseIdAndAllergyAllergen(houseId, allergen);
    }

    private void checkAllergen(HouseAllergyId houseAllergyId) throws EntityNotFoundException {
        if (!houseAllergyRepository.existsById(houseAllergyId))
            throw new EntityNotFoundException(ALLERGEN_NOT_EXIST);
    }

    private void checkStockItem(StockItemId id) throws EntityNotFoundException {
        if (!stockItemRepository.existsById(id))
            throw new EntityNotFoundException(STOCK_ITEM_NOT_EXIST);
    }
}
