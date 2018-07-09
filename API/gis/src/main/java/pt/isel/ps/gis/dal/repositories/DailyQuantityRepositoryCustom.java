package pt.isel.ps.gis.dal.repositories;

import pt.isel.ps.gis.model.DailyQuantity;

import java.sql.Date;
import java.util.List;

public interface DailyQuantityRepositoryCustom {

    /**
     * Find all daily quantities by date interval
     *
     * @param houseId   The id of the house
     * @param sku       The id of the stock item
     * @param startDate Start date, inclusive
     * @param endDate   End date, inclusive
     * @return List with all daily quantities between startDate and endDate
     */
    List<DailyQuantity> findAllByStartDateAndEndDate(Long houseId, String sku, Date startDate, Date endDate);
}
