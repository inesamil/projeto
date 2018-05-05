package pt.isel.ps.gis.dal.repositories;

import pt.isel.ps.gis.model.Product;

public interface ProductRepositoryCustom {

    /**
     * Insert product and return the product inserted.
     *
     * @param product instance of product to insert in db.
     * @return product inserted with product.id
     */
    Product insertProduct(Product product);
}
