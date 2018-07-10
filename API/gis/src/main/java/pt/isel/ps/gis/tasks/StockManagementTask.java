package pt.isel.ps.gis.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pt.isel.ps.gis.bll.StockManagementService;
import pt.isel.ps.gis.dal.repositories.DailyQuantityRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Component
public class StockManagementTask {

    private static final Logger log = LoggerFactory.getLogger(StockManagementTask.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final DailyQuantityRepository dailyQuantityRepository;
    private final StockManagementService stockManagementService;

    public StockManagementTask(DailyQuantityRepository dailyQuantityRepository, StockManagementService stockManagementService) {
        this.dailyQuantityRepository = dailyQuantityRepository;
        this.stockManagementService = stockManagementService;
    }

    @Scheduled(cron = "*/5 * * * * *")
    public void reportCurrentTime() {
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
        log.info("Start at {}", dateFormat.format(date));
        try {
            dailyQuantityRepository.updateDailyQuantity(date);
            stockManagementService.processAllItems();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        date = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
        log.info("End at {}", dateFormat.format(date));
    }
}
