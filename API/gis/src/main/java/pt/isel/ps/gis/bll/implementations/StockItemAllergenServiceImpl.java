package pt.isel.ps.gis.bll.implementations;

import pt.isel.ps.gis.bll.StockItemAllergenService;
import pt.isel.ps.gis.dal.repositories.StockItemAllergyRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.model.Allergy;
import pt.isel.ps.gis.model.StockItem;
import pt.isel.ps.gis.model.StockItemAllergyId;
import pt.isel.ps.gis.utils.ValidationsUtils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class StockItemAllergenServiceImpl implements StockItemAllergenService {

    private final StockItemAllergyRepository stockItemAllergyRepository;

    public StockItemAllergenServiceImpl(StockItemAllergyRepository stockItemAllergyRepository) {
        this.stockItemAllergyRepository = stockItemAllergyRepository;
    }

    @Override
    public boolean existsAllergenByStockItemAllergenId(long houseId, String stockItemSku, String allergen) throws EntityException {
        return stockItemAllergyRepository.existsById(new StockItemAllergyId(houseId, stockItemSku, allergen));
    }

    @Override
    public List<Allergy> getAllergensByStockItemId(long houseId, String stockItemSku) throws EntityException {
        ValidationsUtils.validateHouseId(houseId);
        ValidationsUtils.validateStockItemSku(stockItemSku);
        throw new NotImplementedException();
        //TODO return stockItemAllergyRepository.findAllById_HouseIdAndId_StockitemSku(houseId, stockItemSku);
    }

    @Override
    public List<StockItem> getStockItemsByHouseIdAndAllergenId(long houseId, String allergen) throws EntityException {
        ValidationsUtils.validateHouseId(houseId);
        ValidationsUtils.validateAllergyAllergen(allergen);
        throw new NotImplementedException();
        //TODO return stockItemAllergyRepository.findAllById_HouseIdAndId_AllergyAllergen(houseId, allergen);
    }
}
