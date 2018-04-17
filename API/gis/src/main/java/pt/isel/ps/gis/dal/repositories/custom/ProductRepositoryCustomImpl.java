package pt.isel.ps.gis.dal.repositories.custom;

import org.hibernate.Session;
import pt.isel.ps.gis.dal.repositories.ProductRepositoryCustom;
import pt.isel.ps.gis.model.Product;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.CallableStatement;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void insertProduct(Product product) {
        Session session = entityManager.unwrap(Session.class);
        session.doWork(connection -> {
            try (CallableStatement function = connection.prepareCall(
                    "{call insert_product(?,?,?,?,?)}"
            )) {
                function.setInt(1, product.getId().getCategoryId());
                function.setString(2, product.getProductName());
                function.setBoolean(3, product.getProductEdible());
                function.setShort(4, product.getProductShelflife());
                function.setString(5, product.getProductShelflifetimeunit());
                function.execute();
            }
        });
    }
}
