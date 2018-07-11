package pt.isel.ps.gis.bll.implementations;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.isel.ps.gis.bll.StockItemAllergenService;
import pt.isel.ps.gis.dal.repositories.*;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.exceptions.InsufficientPrivilegesException;
import pt.isel.ps.gis.model.*;
import pt.isel.ps.gis.utils.AuthorizationProvider;
import pt.isel.ps.gis.utils.ValidationsUtils;

import java.util.List;
import java.util.Locale;

@Service
public class StockItemAllergenServiceImpl implements StockItemAllergenService {

    private final StockItemAllergyRepository stockItemAllergyRepository;
    private final AllergyRepository allergyRepository;
    private final StockItemRepository stockItemRepository;
    private final HouseRepository houseRepository;
    private final HouseAllergyRepository houseAllergyRepository;

    private final MessageSource messageSource;

    private final AuthorizationProvider authorizationProvider;

    public StockItemAllergenServiceImpl(StockItemAllergyRepository stockItemAllergyRepository, AllergyRepository allergyRepository, StockItemRepository stockItemRepository, HouseRepository houseRepository, HouseAllergyRepository houseAllergyRepository, MessageSource messageSource, AuthorizationProvider authorizationProvider) {
        this.stockItemAllergyRepository = stockItemAllergyRepository;
        this.allergyRepository = allergyRepository;
        this.stockItemRepository = stockItemRepository;
        this.houseRepository = houseRepository;
        this.houseAllergyRepository = houseAllergyRepository;
        this.messageSource = messageSource;
        this.authorizationProvider = authorizationProvider;
    }

    @Override
    public boolean existsAllergenByStockItemAllergenId(long houseId, String stockItemSku, String allergen) throws EntityException {
        return stockItemAllergyRepository.existsById(new StockItemAllergyId(houseId, stockItemSku, allergen));
    }

    @Transactional
    @Override
    public List<Allergy> getAllergensByStockItemId(String username, long houseId, String stockItemSku, Locale locale) throws EntityException, EntityNotFoundException, InsufficientPrivilegesException {
        checkHouseId(houseId, locale);
        authorizationProvider.checkUserAuthorizationToAccessHouse(username, houseId);
        StockItemId stockItemId = new StockItemId(houseId, stockItemSku);
        checkStockItem(stockItemId, locale);
        return allergyRepository.findAllByHouseIdAndStockitemSku(houseId, stockItemSku);
    }

    @Transactional
    @Override
    public List<StockItem> getStockItemsByHouseIdAndAllergenId(String username, long houseId, String allergen, Locale locale) throws EntityException, EntityNotFoundException, InsufficientPrivilegesException {
        checkHouseId(houseId, locale);
        authorizationProvider.checkUserAuthorizationToAccessHouse(username, houseId);
        HouseAllergyId houseAllergyId = new HouseAllergyId(houseId, allergen);
        checkAllergen(houseAllergyId, locale);
        return stockItemRepository.findAllByHouseIdAndAllergyAllergen(houseId, allergen);
    }

    private void checkHouseId(long houseId, Locale locale) throws EntityException, EntityNotFoundException {
        ValidationsUtils.validateHouseId(houseId);
        if (!houseRepository.existsById(houseId))
            throw new EntityNotFoundException(String.format("House Id %d does not exist.", houseId), messageSource.getMessage("house_Id_Not_Exist", new Object[]{houseId}, locale));
    }

    private void checkAllergen(HouseAllergyId houseAllergyId, Locale locale) throws EntityNotFoundException {
        if (!houseAllergyRepository.existsById(houseAllergyId))
            throw new EntityNotFoundException("Allergen does not exist.", messageSource.getMessage("allergen_Not_Exist", null, locale));
    }

    private void checkStockItem(StockItemId id, Locale locale) throws EntityNotFoundException {
        if (!stockItemRepository.existsById(id))
            throw new EntityNotFoundException("Stock Item does not exist.", messageSource.getMessage("stock_Item_Not_Exist", null, locale));
    }
}
