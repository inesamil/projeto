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
     * @param startDate Start date, exclusive
     * @param endDate   End date, inclusive
     * @return List with all daily quantities between startDate and endDate
     */
    List<DailyQuantity> findAllByStartDateAndEndDate(Long houseId, String sku, Date startDate, Date endDate);

    /**
     * Insert movements final quantity at param date in daily quantity table
     *
     * @param date The date to copy movements final quantity to daily quantity
     */
    void updateDailyQuantity(Date date);
}
