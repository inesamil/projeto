package pt.isel.ps.gis.bll.implementations;

import pt.isel.ps.gis.bll.ProductService;
import pt.isel.ps.gis.dal.repositories.ProductRepository;
import pt.isel.ps.gis.model.Product;
import pt.isel.ps.gis.model.ProductId;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public boolean existsProductByProductId(int categoryId, int productId) {
        return productRepository.existsById(new ProductId(categoryId, productId));
    }

    @Override
    public Optional<Product> getProductByProductId(int categoryId, int productId) {
        return productRepository.findById(new ProductId(categoryId, productId));
    }

    @Override
    public List<ProductService> getProductsByCategoryId(int categoryId) {
        throw new NotImplementedException();
        //TODO
    }

    @Override
    public List<ProductService> getProductsByCategoryIdFiltered(int categoryId, ProductFilters filters) {
        throw new NotImplementedException();
        //TODO
    }
}
