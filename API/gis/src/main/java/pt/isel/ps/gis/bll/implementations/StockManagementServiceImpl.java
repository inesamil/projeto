package pt.isel.ps.gis.bll.implementations;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pt.isel.ps.gis.bll.StockManagementService;
import pt.isel.ps.gis.dal.repositories.StockItemMovementRepository;
import pt.isel.ps.gis.dal.repositories.StockItemRepository;
import pt.isel.ps.gis.model.StockItem;
import pt.isel.ps.gis.model.StockItemMovement;
import pt.isel.ps.gis.stockAlgorithm.Item;
import pt.isel.ps.gis.stockAlgorithm.StockManagementAlgorithm;

import java.sql.Date;
import java.util.List;

@Service
public class StockManagementServiceImpl implements StockManagementService {

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
    private final StockItemMovementRepository stockItemMovementRepository;
    private final StockManagementAlgorithm stockManagementAlgorithm;

    public StockManagementServiceImpl(StockItemRepository stockItemRepository, StockItemMovementRepository stockItemMovementRepository, StockManagementAlgorithm stockManagementAlgorithm) {
        this.stockItemRepository = stockItemRepository;
        this.stockItemMovementRepository = stockItemMovementRepository;
        this.stockManagementAlgorithm = stockManagementAlgorithm;
    }

    @Override
    public void processOneItem(int productId) {

    }

    @Override
    public void processAllItems() {
        int startPage = 0;
        Page<StockItem> all = stockItemRepository.findAll(PageRequest.of(startPage, PAGE_SIZE));
        int totalPages = all.getTotalPages();
        while (startPage < totalPages) {
            startPage++;
            all.forEach(stockItem -> {
                long millis = System.currentTimeMillis();
                Date startDate = new Date(millis - (WEEKS_TO_ESTIMATE * WEEK_IN_MS));
                Date endDate = new Date(millis);
                List<StockItemMovement> movements = stockItemMovementRepository.findAllByStartDateAndEndDate(startDate, endDate);

                Item[] items = new Item[WEEKS_TO_ESTIMATE * WEEK_IN_DAYS];
                for (StockItemMovement movement : movements) {

                }


                Item[] nextWeek = stockManagementAlgorithm.estimateNextWeek(items);
                for (Item item : nextWeek) {
                    // if (item.getQuantity() < 5)
                    // addToSystemList();
                }
            });
        }
    }
}
