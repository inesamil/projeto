package pt.isel.ps.gis.bll.implementations;

import org.springframework.context.MessageSource;
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
import java.util.Locale;

@Service
public class StockItemAllergenServiceImpl implements StockItemAllergenService {

    private final StockItemAllergyRepository stockItemAllergyRepository;
    private final AllergyRepository allergyRepository;
    private final StockItemRepository stockItemRepository;
    private final HouseAllergyRepository houseAllergyRepository;

    private final MessageSource messageSource;

    public StockItemAllergenServiceImpl(StockItemAllergyRepository stockItemAllergyRepository, AllergyRepository allergyRepository, StockItemRepository stockItemRepository, HouseAllergyRepository houseAllergyRepository, MessageSource messageSource) {
        this.stockItemAllergyRepository = stockItemAllergyRepository;
        this.allergyRepository = allergyRepository;
        this.stockItemRepository = stockItemRepository;
        this.houseAllergyRepository = houseAllergyRepository;
        this.messageSource = messageSource;
    }

    @Override
    public boolean existsAllergenByStockItemAllergenId(long houseId, String stockItemSku, String allergen) throws EntityException {
        return stockItemAllergyRepository.existsById(new StockItemAllergyId(houseId, stockItemSku, allergen));
    }

    @Transactional
    @Override
    public List<Allergy> getAllergensByStockItemId(long houseId, String stockItemSku, Locale locale) throws EntityException, EntityNotFoundException {
        StockItemId stockItemId = new StockItemId(houseId, stockItemSku);
        checkStockItem(stockItemId, locale);
        return allergyRepository.findAllByHouseIdAndStockitemSku(houseId, stockItemSku);
    }

    @Transactional
    @Override
    public List<StockItem> getStockItemsByHouseIdAndAllergenId(long houseId, String allergen, Locale locale) throws EntityException, EntityNotFoundException {
        HouseAllergyId houseAllergyId = new HouseAllergyId(houseId, allergen);
        checkAllergen(houseAllergyId, locale);
        return stockItemRepository.findAllByHouseIdAndAllergyAllergen(houseId, allergen);
    }

    private void checkAllergen(HouseAllergyId houseAllergyId, Locale locale) throws EntityNotFoundException {
        if (!houseAllergyRepository.existsById(houseAllergyId))
            throw new EntityNotFoundException(messageSource.getMessage("allergen_Not_Exist", null, locale));
    }

    private void checkStockItem(StockItemId id, Locale locale) throws EntityNotFoundException {
        if (!stockItemRepository.existsById(id))
            throw new EntityNotFoundException(messageSource.getMessage("stock_Item_Not_Exist", null, locale));
    }
}
