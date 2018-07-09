package pt.isel.ps.gis.bll.implementations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pt.isel.ps.gis.bll.StockManagementService;
import pt.isel.ps.gis.dal.repositories.DailyQuantityRepository;
import pt.isel.ps.gis.dal.repositories.ListProductRepository;
import pt.isel.ps.gis.dal.repositories.StockItemRepository;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.model.DailyQuantity;
import pt.isel.ps.gis.model.ListProduct;
import pt.isel.ps.gis.model.StockItem;
import pt.isel.ps.gis.model.StockItemId;
import pt.isel.ps.gis.stockAlgorithm.Item;
import pt.isel.ps.gis.stockAlgorithm.StockManagementAlgorithm;

import javax.persistence.EntityNotFoundException;
import java.sql.Date;
import java.util.List;
import java.util.Locale;

@Service
public class StockManagementServiceImpl implements StockManagementService {

    private static final Logger log = LoggerFactory.getLogger(StockManagementServiceImpl.class);

    private static final int PAGE_SIZE = 50;
    private static final short WEEKS_TO_ESTIMATE = 3;

    private static final short SECOND_IN_MS = 1000; // 1 second is 1000 miliseconds
    private static final short MINUTE_IN_SECONDS = 60; // 1 minute is 60 seconds
    private static final short HOUR_IN_MINUTES = 60; // 1 hour is 60 minutes
    private static final short DAY_IN_HOURS = 24; // 1 day is 24 hours
    private static final short WEEK_IN_DAYS = 7; // 1 week is 7 days

    private static final long DAY_IN_MS = SECOND_IN_MS * MINUTE_IN_SECONDS * HOUR_IN_MINUTES * DAY_IN_HOURS;
    private static final long WEEK_IN_MS = WEEK_IN_DAYS * DAY_IN_MS;

    private static final short THRESHOLD = 2;

    private final StockItemRepository stockItemRepository;
    private final DailyQuantityRepository dailyQuantityRepository;
    private final StockManagementAlgorithm stockManagementAlgorithm;
    private final ListProductRepository listProductRepository;
    private final MessageSource messageSource;

    public StockManagementServiceImpl(StockItemRepository stockItemRepository, DailyQuantityRepository dailyQuantityRepository, StockManagementAlgorithm stockManagementAlgorithm, ListProductRepository listProductRepository, MessageSource messageSource) {
        this.stockItemRepository = stockItemRepository;
        this.dailyQuantityRepository = dailyQuantityRepository;
        this.stockManagementAlgorithm = stockManagementAlgorithm;
        this.listProductRepository = listProductRepository;
        this.messageSource = messageSource;
    }

    @Override
    public void processOneItem(long houseId, String stockitemSku) throws EntityException {
        Locale locale = LocaleContextHolder.getLocale();
        StockItem stockItem = stockItemRepository
                .findById(new StockItemId(houseId, stockitemSku))
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("stock_Item_Id_Not_Exist", new Object[]{houseId, stockitemSku}, locale)));
        processOneItem(stockItem);
    }

    @Override
    public void processAllItems() {
        int startPage = 0;
        Page<StockItem> all = stockItemRepository.findAll(PageRequest.of(startPage, PAGE_SIZE));
        int totalPages = all.getTotalPages();
        while (true) {
            startPage++;
            all.forEach(this::processOneItem);
            if (startPage >= totalPages)
                break;
            all = stockItemRepository.findAll(PageRequest.of(startPage, PAGE_SIZE));
        }
    }

    private void processOneItem(StockItem stockItem) {
        long millis = System.currentTimeMillis();
        Date startDate = new Date(millis - (WEEKS_TO_ESTIMATE * WEEK_IN_MS));
        Date endDate = new Date(millis);
        StockItemId id = stockItem.getId();
        List<DailyQuantity> quantities = dailyQuantityRepository
                .findAllByStartDateAndEndDate(id.getHouseId(), id.getStockitemSku(), startDate, endDate);
        int size = WEEKS_TO_ESTIMATE * WEEK_IN_DAYS;
        if (quantities.size() != size)
            return;
        Item[] items = new Item[size];

        for (int i = 0; i < quantities.size(); i++)
            items[i] = new Item(null, quantities.get(i).getDailyquantity_quantity(), null);

        Item[] nextWeek = stockManagementAlgorithm.estimateNextWeek(items);

        short min = Short.MAX_VALUE, max = Short.MIN_VALUE;
        for (Item item : nextWeek) {
            short quantity = item.getQuantity();
            if (quantity < min) {
                min = quantity;
            }
            if (quantity > max) {
                max = quantity;
            }
        }

        if (min < THRESHOLD) {
            addToSystemList(stockItem, (short) (max - min));
        }
    }

    private void addToSystemList(StockItem stockItem, short quantity) {
        try {
            listProductRepository.save(new ListProduct(stockItem.getId().getHouseId(), (short) 1, stockItem.getProductId(), stockItem.getStockitemBrand(), quantity));
        } catch (EntityException e) {
            log.warn(stockItem.getId().getHouseId() + ", " + stockItem.getId().getStockitemSku() + ", " + stockItem.getProductId());
            log.warn(e.getMessage());
        }
    }
}
