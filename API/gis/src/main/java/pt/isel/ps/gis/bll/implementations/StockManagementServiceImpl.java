package pt.isel.ps.gis.bll.implementations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pt.isel.ps.gis.bll.StockManagementService;
import pt.isel.ps.gis.dal.repositories.DailyQuantityRepository;
import pt.isel.ps.gis.dal.repositories.ListProductRepository;
import pt.isel.ps.gis.dal.repositories.StockItemRepository;
import pt.isel.ps.gis.dal.repositories.SystemListRepository;
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

    private final StockItemRepository stockItemRepository;
    private final DailyQuantityRepository dailyQuantityRepository;
    private final StockManagementAlgorithm stockManagementAlgorithm;
    private final SystemListRepository systemListRepository;
    private final ListProductRepository listProductRepository;

    public StockManagementServiceImpl(StockItemRepository stockItemRepository, DailyQuantityRepository dailyQuantityRepository, StockManagementAlgorithm stockManagementAlgorithm, SystemListRepository systemListRepository, ListProductRepository listProductRepository) {
        this.stockItemRepository = stockItemRepository;
        this.dailyQuantityRepository = dailyQuantityRepository;
        this.stockManagementAlgorithm = stockManagementAlgorithm;
        this.systemListRepository = systemListRepository;
        this.listProductRepository = listProductRepository;
    }

    @Override
    public void processOneItem(long houseId, String stockitemSku) throws EntityException {
        StockItem stockItem = stockItemRepository
                .findById(new StockItemId(houseId, stockitemSku))
                .orElseThrow(() -> new EntityNotFoundException("")); // TODO meter mensagem
        processOneItem(stockItem);
    }

    @Override
    public void processAllItems() {
        int startPage = 0;
        Page<StockItem> all = stockItemRepository.findAll(PageRequest.of(startPage, PAGE_SIZE));
        int totalPages = all.getTotalPages();
        while (startPage < totalPages) {
            startPage++;
            all.forEach(this::processOneItem);
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

        for (int i = nextWeek.length - 1; i >= nextWeek.length - WEEK_IN_DAYS; i--) {
            if (nextWeek[i].getQuantity() < 5) {
                addToSystemList(stockItem, nextWeek[i].getQuantity());
                break;
            }
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

    private class Tuple {

        public StockItem stockItem;
        public short quantity;

        public Tuple(StockItem stockItem, short quantity) {
            this.stockItem = stockItem;
            this.quantity = quantity;
        }
    }
}
