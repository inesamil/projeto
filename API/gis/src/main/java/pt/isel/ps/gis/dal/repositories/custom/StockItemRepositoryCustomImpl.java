package pt.isel.ps.gis.dal.repositories.custom;

import org.hibernate.Session;
import pt.isel.ps.gis.dal.repositories.StockItemRepositoryCustom;
import pt.isel.ps.gis.model.StockItem;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.CallableStatement;

public class StockItemRepositoryCustomImpl implements StockItemRepositoryCustom {


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void insertStockItem(StockItem stockItem) {
        Session session = entityManager.unwrap(Session.class);
        session.doWork(connection -> {
            try (CallableStatement function = connection.prepareCall(
                    "{call insert_stock_item(?,?,?,?,?,?,?,?,?,?)}"
            )) {
                function.setLong(1, stockItem.getId().getHouseId());
                function.setInt(2, stockItem.getCategoryId());
                function.setInt(3, stockItem.getProductId());
                function.setString(4, stockItem.getStockitemBrand());
                function.setString(5, stockItem.getStockitemVariety());
                function.setFloat(6, stockItem.getStockitemSegment());
                function.setString(7, stockItem.getStockitemSegmentunit());
                function.setShort(8, stockItem.getStockitemQuantity());
                function.setString(9, stockItem.getStockitemDescription());
                function.setString(10, stockItem.getStockitemConservationstorage());
                function.execute();
            }
        });
    }
}
